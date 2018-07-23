package com.example.thymeleaf.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixiaoming on 2018/7/14.
 */
@Component
public class KKCrawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "http://www.12kanshu.com";

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        final String cssQuery = "#chapterContent p";
        return parseLevel3(crawlerId, url, cssQuery);
    }

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        final String cssQuery = "td.chapterBean a";
        return parseLevel2(crawlerId, url, cssQuery,url);
    }

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean isMatch(String content) {
        //24330165.html
        Pattern pattern = Pattern.compile("\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
