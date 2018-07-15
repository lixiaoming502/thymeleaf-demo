package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

/**
 * job1:find article brother
 * job2:find brother list
 * Created by lixiaoming on 2018/7/13.
 */
@Component
public class BrotherCroner {

    private static Log logger = LogFactory.getLog(BrotherCroner.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BrotherService brotherService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    private static final int MAXLEN = 100;

    @Scheduled(fixedDelay = 3000)
    public void work(){
        findArticleBrother();
        findBrotherChapterList();
    }

    private void findBrotherChapterList() {
        int waitLen = futureCrawlerService.getWaitingLength();
        if(waitLen>MAXLEN){
            return;
        }
        List<Brother> lst = brotherService.selectByUpdateTurns(0);
        lst.stream().forEach(brother -> {
            try {
                final String brotherUrl = brother.getBrotherUrl();
                String domainName = AppUtils.extraDomain(brotherUrl);
                FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomain(domainName);
                Integer domainId = cfg.getId();
                int level = 2;
                String crawlerState = "A";
                FutureCrawler futureCrawler = new FutureCrawler();
                futureCrawler.setCrawlerState(crawlerState);
                futureCrawler.setDomainId(domainId);
                futureCrawler.setPageUrl(brotherUrl);
                futureCrawler.setCreateTime(new Date());
                futureCrawler.setSeedId(cfg.getDefaultParserSeed());
                futureCrawler.setLevel(level);
                futureCrawler.setCallbackStatus(0);
                futureCrawler.setCallbackBean("brotherCallBack");
                futureCrawler.setCallbackLevel(2);
                futureCrawlerService.addNew(futureCrawler);

                brother.setUpdateTurns(brother.getUpdateTurns()+1);
                brotherService.update(brother);

            } catch (MalformedURLException e) {
                logger.warn("",e);
            }
        });
    }

    private void findArticleBrother() {
        List<Article> articles = articleService.getNoBrothers();
        articles.stream().forEach(article->{
            String title = article.getTitle();
            List<String> urls = searchService.searchAll(title);
            if(CollectionUtils.isEmpty(urls)){
                article.setBrotherState("E");
            }else{
                for(String url:urls){
                    Brother brother = new Brother();
                    brother.setArticleId(article.getId());
                    brother.setBrotherUrl(url);
                    brother.setUpdateTurns(0);
                    brotherService.insert(brother);
                }
                article.setBrotherState("F");
            }
            articleService.update(article);
        });
    }
}
