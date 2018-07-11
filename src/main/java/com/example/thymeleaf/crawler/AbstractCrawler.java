package com.example.thymeleaf.crawler;

import com.example.thymeleaf.selfdrive.DefaultSelfDriver;
import com.example.thymeleaf.selfdrive.DriverFuture;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lixiaoming on 2018/6/29.
 */
public abstract class AbstractCrawler implements Crawler {
    @Autowired
    protected DefaultSelfDriver selfDriver;

    @Autowired
    protected FutureCrawlerService futureCrawlerService;

    @Autowired
    protected FuturePageLoaderService futurePageLoaderService;

    public Integer domainId;


    public boolean parse(int level, int crawlerId, Integer domainId, String url){
        this.domainId = domainId;
        switch (level){
            case 1:
                return praseLevel1(crawlerId,url);
            case 2:
                return parseLevel2(crawlerId,url);
        }
        return false;
    }

    protected abstract boolean parseLevel2(int crawlerId, String url);

    protected abstract boolean praseLevel1(int crawlerId, String url);


    protected DriverFuture createDriverFuture(int crawlerId, String url) {
        DriverFuture future = new DriverFuture(crawlerId,domainId,url);
        selfDriver.fetch(future);
        return future;
    }


    /**
     * 判断是否有页面待抓取
     * @param crawlerId
     * @return
     */
    protected boolean hasCrawlerPageWaiting(int crawlerId) {
        return futurePageLoaderService.isCrawlerPageWaiting(crawlerId);
    }
}
