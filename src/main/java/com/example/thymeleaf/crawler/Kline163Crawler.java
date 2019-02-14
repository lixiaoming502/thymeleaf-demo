package com.example.thymeleaf.crawler;

import com.example.thymeleaf.selfdrive.DriverFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by lixiaoming on 2019/2/13.
 */
@Component
public class Kline163Crawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(Kline163Crawler.class);

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            String response = (String)future.getRespone();
            try {
                String crawlerState = "F";
                futureCrawlerService.finish(crawlerId,crawlerState,response);
            } catch (IOException e) {
                logger.warn("",e);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected String sensitiveWordsFilter(String htmlContent) {
        return null;
    }

    @Override
    protected boolean isMatch(String content) {
        return false;
    }
}
