package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.service.ArticleService;
import com.example.thymeleaf.service.ChapterService;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 *
 * <p>chaptelist更新调度器</p>
 */
@Component
public class ChapterCallBackCroner {

    private static Log logger = LogFactory.getLog(ChapterCallBackCroner.class);

    private static final int MAXLEN = 100;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Scheduled(fixedDelay = 10000)
    public void work(){
        logger.info("ChapterCallBackCroner start");
        int waitLen = futureCrawlerService.getWaitingLength();
        if(waitLen<MAXLEN){
            //int maxtArticleId = chapterService.getMaxArticlId();
            //logger.info("maxtArticleId:"+maxtArticleId);
            List<Article> toBeCalls = articleService.getToBeUpdate();
            for(Article article:toBeCalls){
                logger.info("article_id:"+article.getId());
                updateChapterList(article);
            }
        }else{
            logger.info("waitLen:"+waitLen);
        }
        logger.info("FutureCrawlerCallBackCroner end");
    }

    private void updateChapterList(Article article) {
        String listUrl = article.getListUrl();

        String domainName = null;
        try {
            domainName = AppUtils.extraDomain(listUrl);
            FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomain(domainName);
            Integer domainId = cfg.getId();
            int level = 2;
            String crawlerState = "A";
            FutureCrawler futureCrawler = new FutureCrawler();
            futureCrawler.setCrawlerState(crawlerState);
            futureCrawler.setDomainId(domainId);
            futureCrawler.setPageUrl(listUrl);
            futureCrawler.setCreateTime(new Date());
            futureCrawler.setSeedId(article.getSeedId());
            futureCrawler.setLevel(level);
            futureCrawler.setCallbackStatus(0);
            futureCrawler.setCallbackBean("articleCallBack");
            futureCrawler.setCallbackLevel(2);
            futureCrawlerService.addNew(futureCrawler);

            article.setModifyDate(AppUtils.getCurrentDay());
            articleService.update(article);
        } catch (MalformedURLException e) {
            logger.warn("",e);
        }

    }

}
