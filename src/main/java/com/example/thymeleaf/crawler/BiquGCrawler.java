package com.example.thymeleaf.crawler;

/**
 * Created by lixiaoming on 2018/7/4.
 */
public class BiquGCrawler extends AbstractCrawler {

    private String baseUrl = "https://www.xxbiquge.com";

    //https://www.xxbiquge.com/66_66231/

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected boolean isMatch(String content) {
        return false;
    }

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        return false;
    }

}
