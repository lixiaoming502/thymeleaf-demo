package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 *
 * <p>章节内容抓取调度</p>
 */
@Component
public class ChapterContentCallBackCroner {

    private static Log logger = LogFactory.getLog(ChapterContentCallBackCroner.class);

    private static final int MAXLEN = 100;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private BrotherChapterService brotherChapterService;

    //@Scheduled(fixedDelay = 10000)
    public void work(){
        logger.info("ChapterContentCallBackCroner start");
        int waitLen = futureCrawlerService.getWaitingLength();
        if(waitLen<MAXLEN){
            List<Chapter> toBeCalls = chapterService.getToBeCrawl();
            toBeCalls.stream().forEach(chapter -> {
                updateChapter(chapter);
            });
        }else{
            logger.info("waitLen:"+waitLen);
        }
        logger.info("ChapterContentCallBackCroner end");
    }

    public void recrawl(int articleId){
        List<Chapter> lst = chapterService.selectAllByArticleId(articleId);
        for(Chapter chapter:lst){
            updateChapter(chapter);
        }
    }

    private void updateChapter(Chapter chapter) {
        int chapterId = chapter.getId();
        String url = chapter.getUrl();
        List<String> urls = new ArrayList<>();
        urls.add(url);
        List<BrotherChapter> lst = brotherChapterService.queryByChapterId(chapterId);
        if(CollectionUtils.isNotEmpty(lst)){
            lst.stream().forEach(brotherChapter -> {
                urls.add(brotherChapter.getUrl());
            });
        }
       String seleURL =  selectByQueueSize(urls);
        logger.info("seleURL:"+seleURL+" chapterId."+chapterId);
       FutureCrawlerCfg futureCrawlerCfg = queryByUrl(seleURL);
        int level = 3;
        String crawlerState = "A";
        FutureCrawler futureCrawler = new FutureCrawler();
        futureCrawler.setCrawlerState(crawlerState);
        futureCrawler.setDomainId(futureCrawlerCfg.getId());
        futureCrawler.setPageUrl(seleURL);
        futureCrawler.setCreateTime(new Date());
        futureCrawler.setSeedId(futureCrawlerCfg.getDefaultParserSeed());
        futureCrawler.setLevel(level);
        futureCrawler.setCallbackStatus(0);
        futureCrawler.setCallbackBean("articleCallBack");
        futureCrawler.setCallbackLevel(3);
        futureCrawler.setRefId(chapterId);
        futureCrawlerService.addNew(futureCrawler);

        //更新collect_flag = 1,但是有可能在采集中，末位I作为区分
        chapter.setCollectFlag(true);
        chapter.setLocalUrl(seleURL+"I");
        chapterService.update(chapter);
    }

    private String selectByQueueSize(List<String> urls) {
        Collections.sort(urls, (o1, o2) -> {
            int len1 = getInCrawlerLen(o1);
            int len2 = getInCrawlerLen(o2);
            return len1-len2;
        });
        return urls.get(0);
    }

    private FutureCrawlerCfg queryByUrl(String url){
        String domainName = null;
        try {
            domainName = AppUtils.extraDomain(url);
        } catch (MalformedURLException e) {
            logger.warn("illegal url."+url,e);
            return null;
        }
        FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomain(domainName);
        return cfg;
    }

    private int getInCrawlerLen(String o1)  {
        FutureCrawlerCfg cfg = queryByUrl(o1);
        if(cfg!=null&&cfg.getEnable().equals("Y")){
            int len =  futureCrawlerService.getMaxLen(cfg.getId());
            return len;
        }
        return 999;
    }
}
