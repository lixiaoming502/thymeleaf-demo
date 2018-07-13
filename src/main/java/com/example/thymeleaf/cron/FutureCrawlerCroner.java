package com.example.thymeleaf.cron;

import com.example.thymeleaf.crawler.Crawler;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.service.ApplicationContextProvider;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
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
 *
 * <p>种子爬取调度器</p>
 */
@Component
public class FutureCrawlerCroner {
    private static Log logger = LogFactory.getLog(FutureCrawlerCroner.class);

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private SeedsService seedsService;

    @Scheduled(fixedDelay = 1000)
    public void work(){
        logger.info("FutureCrawlerCroner start");
        //1.找到不同的domain_id
        List<FutureCrawlerCfg> lst = futureCrawlerCfgService.getAll();
        //2.对于每个domain_id,找到第一个最小记录，执行爬取
        for(FutureCrawlerCfg futureCrawlerCfg:lst){
            FutureCrawler futureCrawler = futureCrawlerService.getToBeCrawlByDomainId(futureCrawlerCfg.getId());
            if(futureCrawler!=null){
                crawl(futureCrawler);
            }
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
        boolean parsed  = crawler.parse(level,crawlerId,futureCrawler.getDomainId(),url);
        if(!parsed) {
            futureCrawler.setCrawlerState("P");
            futureCrawler.setUpdateTime(new Date());
            futureCrawlerService.update(futureCrawler);
        }
    }

}
