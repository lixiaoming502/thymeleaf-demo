package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.JoddHttp;
import com.example.thymeleaf.util.AppUtils;
import jodd.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class FuturePageLoaderCroner {

    private static Log logger = LogFactory.getLog("timeLog");

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @Autowired
    private JoddHttp joddHttp;

    @Scheduled(fixedRate = 1000)
    public void work(){
        logger.info("FuturePageLoaderCroner begin");
        List<FuturePageLoader> toBeLoaded = futurePageLoaderService.getToBeLoaded();
        //状态由A->P
        for(FuturePageLoader futurePageLoader:toBeLoaded){
            loadPage(futurePageLoader);
        }
        List<FuturePageLoader> toBeCrawler = futurePageLoaderService.getToBeCrawler();
        for(FuturePageLoader futurePageLoader:toBeCrawler){
            crawl(futurePageLoader);
        }
        logger.info("FuturePageLoaderCroner end");

    }

    private void crawl(FuturePageLoader futurePageLoader) {
        logger.info("strat crawl ["+futurePageLoader.getPageUrl()+"]");
        HttpResponse response = joddHttp.sendBrowser(futurePageLoader.getPageUrl(),futurePageLoader.getPageUrl());
        int statusCode = response.statusCode();
        if(statusCode!=200){
            logger.info("get statusCode "+statusCode);
            logger.info("updatePlanTime id = "+futurePageLoader.getId());
            futurePageLoaderService.updatePlanTime(futurePageLoader);
            return;
        }
        //更新状态，写文件，如果写文件异常则回滚
        try {
            String body = new String(response.bodyBytes(),"utf-8");
            futurePageLoaderService.loadComplete(futurePageLoader.getId(),statusCode,body);
        }catch (Exception e){
            logger.warn("",e);
        }
    }

    private void loadPage(FuturePageLoader futurePageLoader) {
        try {
            String pageUrl = futurePageLoader.getPageUrl();
            String domainName = AppUtils.extraDomain(pageUrl);
            futurePageLoaderService.updateCrawlState(domainName,futurePageLoader.getId());
        } catch (Exception e) {
            logger.warn("",e);
        }
    }

}
