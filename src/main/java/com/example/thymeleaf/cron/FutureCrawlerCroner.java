package com.example.thymeleaf.cron;

import com.example.thymeleaf.crawler.Crawler;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.ApplicationContextProvider;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.SeedsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class FutureCrawlerCroner {
    private static Log logger = LogFactory.getLog("timeLog");

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private SeedsService seedsService;

    @Scheduled(fixedRate = 5000)
    public void work(){
        logger.info("FutureCrawlerCroner start");
        List<FutureCrawler> lst = futureCrawlerService.getToBeCrawl();
        for(FutureCrawler futureCrawler:lst){
            crawl(futureCrawler);
        }
        logger.info("FutureCrawlerCroner end");
    }

    private void crawl(FutureCrawler futureCrawler) {
        int seedId = futureCrawler.getSeedId();
        int level = futureCrawler.getLevel();
        String url = futureCrawler.getPageUrl();
        int crawlerId = futureCrawler.getId();

        String beanName = seedsService.getBeanName(seedId);
        Crawler crawler = (Crawler) ApplicationContextProvider.getBean(beanName);
        boolean parsed  = crawler.parse(level,crawlerId,url);
        if(!parsed) {
            futureCrawler.setCrawlerState("P");
            futureCrawler.setUpdateTime(new Date());
            futureCrawlerService.update(futureCrawler);
        }
    }

}
