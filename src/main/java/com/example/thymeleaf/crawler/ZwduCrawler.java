package com.example.thymeleaf.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lixiaoming on 2018/7/12.
 */
@Component
public class ZwduCrawler extends AbstractCrawler{

    private static Logger logger = LoggerFactory.getLogger(ZwduCrawler.class);

    private String rootURL = "http://www.zwdu.com";

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        final String cssQuery = "#list dl dd a";
        return parseLevel2(crawlerId, url, cssQuery,rootURL);
    }


    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean isMatch(String content) {
        return false;
    }
}
