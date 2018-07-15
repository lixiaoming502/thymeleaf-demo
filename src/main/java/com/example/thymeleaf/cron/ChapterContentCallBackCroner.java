package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.service.ArticleService;
import com.example.thymeleaf.service.ChapterService;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
import com.example.thymeleaf.service.FutureCrawlerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private ArticleService articleService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    //@Scheduled(fixedDelay = 10000)
    public void work(){
        logger.info("ChapterContentCallBackCroner start");
        int waitLen = futureCrawlerService.getWaitingLength();
        if(waitLen<MAXLEN){
            List<Chapter> toBeCalls = chapterService.getToBeCrawl();
            for(Chapter chapter:toBeCalls){
                updateChapter(chapter);
            }
        }else{
            logger.info("waitLen:"+waitLen);
        }
        logger.info("ChapterContentCallBackCroner end");
    }

    private void updateChapter(Chapter chapter) {
        //TODO:

    }



}
