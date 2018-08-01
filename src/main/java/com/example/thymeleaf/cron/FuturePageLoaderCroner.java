package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.JoddHttp;
import com.example.thymeleaf.util.AppUtils;
import jodd.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class FuturePageLoaderCroner {

    private static Log logger = LogFactory.getLog(FuturePageLoaderCroner.class);

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @Autowired
    private JoddHttp joddHttp;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    private Executor executor = Executors.newFixedThreadPool(5);

    //@Scheduled(fixedDelay = 1000)
    public void work(){
        logger.info("FuturePageLoaderCroner begin");
        //1.找到不同的domain_id
        List<FutureCrawlerCfg> lst = futureCrawlerCfgService.getAll();
        //2.对于每个domain_id,找到第一个最小记录，执行爬取
        ArrayList<FutureTask> futures = new ArrayList<>();
        for(FutureCrawlerCfg futureCrawlerCfg:lst){
            FuturePageLoader futurePageLoader = futurePageLoaderService.getToBeCrawlByDomainId(futureCrawlerCfg.getId());
            if(futurePageLoader!=null){
                Callable<Void> callable = () -> {
                    Date last = futureCrawlerCfg.getLastCrawlTime();
                    Integer gap = futureCrawlerCfg.getGap();
                    Date next  = AppUtils.addSecond(gap,last);
                    Date now = new Date();
                    if(next.before(now)){
                        crawl(futurePageLoader,futureCrawlerCfg.getCharset());
                        final Date afterCrawlerTime = new Date();
                        futureCrawlerCfg.setLastCrawlTime(afterCrawlerTime);
                        futureCrawlerCfgService.update(futureCrawlerCfg);
                    }
                    return null;
                };
                FutureTask<Void> futureTask = new FutureTask<>(callable);
                futures.add(futureTask);
                executor.execute(futureTask);
            }
        }
        futures.stream().forEach(futureTask -> {
            try {
                futureTask.get(3,TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                logger.warn("",e);
            } catch (ExecutionException e) {
                logger.warn("",e);
            } catch (TimeoutException e) {
                logger.warn("",e);
            }
        });
        logger.info("FuturePageLoaderCroner end");
    }

    private void crawl(FuturePageLoader futurePageLoader,String charset ) {
        logger.info("strat crawl ["+futurePageLoader.getPageUrl()+"]");
        try {
            HttpResponse response = joddHttp.sendBrowser(futurePageLoader.getPageUrl(),futurePageLoader.getPageUrl());
            int statusCode = response.statusCode();
            if(statusCode!=200){
                logger.warn("get statusCode "+statusCode+" recrawl later");
                response = joddHttp.sendBrowser(futurePageLoader.getPageUrl(),futurePageLoader.getPageUrl());
                statusCode = response.statusCode();
                logger.warn(" recrawl get statusCode "+statusCode);
            }
            String body = new String(response.bodyBytes(),charset);
            //更新状态，写文件，如果写文件异常则回滚
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
