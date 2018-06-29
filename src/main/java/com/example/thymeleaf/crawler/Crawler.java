package com.example.thymeleaf.crawler;

/**
 * Created by lixiaoming on 2018/6/28.
 */
public interface Crawler {
    public boolean parse(int level,int crawlerId,String url);
}
