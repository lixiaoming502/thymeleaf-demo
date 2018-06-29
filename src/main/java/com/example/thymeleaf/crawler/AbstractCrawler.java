package com.example.thymeleaf.crawler;

import com.example.thymeleaf.selfdrive.DefaultSelfDriver;
import com.example.thymeleaf.selfdrive.DriverFuture;
import com.example.thymeleaf.service.FutureCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lixiaoming on 2018/6/29.
 */
public abstract class AbstractCrawler implements Crawler {
    @Autowired
    protected DefaultSelfDriver selfDriver;

    @Autowired
    protected FutureCrawlerService futureCrawlerService;


    protected DriverFuture createDriverFuture(int crawlerId, String url) {
        DriverFuture future = new DriverFuture(crawlerId,url);
        selfDriver.fetch(future);
        return future;
    }
}
