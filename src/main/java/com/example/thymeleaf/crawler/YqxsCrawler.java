package com.example.thymeleaf.crawler;

import com.example.thymeleaf.service.MarkCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MarkCleaner markCleaner;

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
    protected String sensitiveWordsFilter(String htmlContent) {
        //<br>　　请记住本书首发域名：www.yqxs.cc。言情小说网手机版阅读网址：m.yqxs.cc
        String rr = markCleaner.replaceWithStrDelimer(htmlContent,"<br>　　请记住本书首发域名","m.yqxs.cc");
        //<br>　　http://www.yqxs.cc/html/64/64352/5126700.html
        rr = markCleaner.replaceWithStrDelimer(rr,"<br>　　http://www.yqxs.cc","html");

        if(rr.length()/htmlContent.length()*100<80){
            logger.info("sensitiveWordsFilter may be error");
            return rr;
        }else{
            return htmlContent;
        }
    }

    @Override
    protected boolean isMatch(String content) {
        // /html/102/102846/24330165.html
        Pattern pattern = Pattern.compile("/html/\\d+/\\d+/\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
