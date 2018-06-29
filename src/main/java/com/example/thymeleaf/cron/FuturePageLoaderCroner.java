package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.JoddHttp;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.List;


/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class FuturePageLoaderCroner {

    private static Logger logger = LoggerFactory.getLogger(FuturePageLoaderCroner.class);

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
        HttpResponse response = joddHttp.send(futurePageLoader.getPageUrl());
        int statusCode = response.statusCode();
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
            String domainName = extraDomain(pageUrl);
            futurePageLoaderService.updateCrawlState(domainName,futurePageLoader.getId());
        } catch (Exception e) {
            logger.warn("",e);
        }
    }

    private String extraDomain(String pageUrl) throws MalformedURLException {
        java.net.URL  url = new  java.net.URL(pageUrl);
        String host = url.getHost();// 获取主机名
        return host;
    }
}
