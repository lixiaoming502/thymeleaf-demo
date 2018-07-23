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
public class YqxsCrawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "http://www.yqxs.cc";

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        //#content
        final String cssQuery = "#content";
        return parseLevel3(crawlerId, url, cssQuery);
    }

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        //body > div.listmain > dl > dd:nth-child(6) > a
        final String cssQuery = "div.listmain dl dd a";
        return parseLevel2(crawlerId, url, cssQuery,baseUrl);
    }

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean isMatch(String content) {
        // /html/102/102846/24330165.html
        Pattern pattern = Pattern.compile("/html/\\d+/\\d+/\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
