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
public class LWCrawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "https://www.lewen8.com";

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        //#chapterlist > ul:nth-child(22) > li:nth-child(49) > a
        final String cssQuery = "#chapterlist a";
        return parseLevel2(crawlerId, url, cssQuery,baseUrl);
    }

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean isMatch(String content) {
        ///lw21321/1869332.html
        Pattern pattern = Pattern.compile("/lw\\d+./\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
