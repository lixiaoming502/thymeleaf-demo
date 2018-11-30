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
public class KKCrawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    @Autowired
    private  MarkCleaner markCleaner;

    private String baseUrl = "http://www.12kanshu.com";

    KKCrawler(){
        this.level3FilterWords = "看书小说网www.12kanshu.com";
    }

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        final String cssQuery = "#chapterContent";
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
    protected String sensitiveWordsFilter(String htmlContent) {
        //按行读取，找到read_tit前的都抛弃
        String rr = markCleaner.cleanLines(htmlContent,0,2);
        rr = markCleaner.replaceWithStrDelimer(rr,"<br>　　老铁!还在找","</p>");
        if(rr.length()/htmlContent.length()*100<80){
            logger.info("sensitiveWordsFilter may be error");
            return rr;
        }else{
            return htmlContent;
        }
    }

    @Override
    protected boolean isMatch(String content) {
        //24330165.html
        Pattern pattern = Pattern.compile("\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
