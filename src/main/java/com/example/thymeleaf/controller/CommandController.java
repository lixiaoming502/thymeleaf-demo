package com.example.thymeleaf.controller;

import com.example.thymeleaf.cron.ByStanderWooniuSynCroner;
import com.example.thymeleaf.cron.ChapterContentCallBackCroner;
import com.example.thymeleaf.cron.FutureCrawlerCroner;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.ArticleService;
import com.example.thymeleaf.service.BaiduBrother;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.SynBrotherChapterId;
import com.example.thymeleaf.vo.SearchResultPage;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2018/9/30.
 */
@RestController
@RequestMapping("/command")
public class CommandController {
    private static Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    private SynBrotherChapterId synBrotherChapterId;

    @Autowired
    private ChapterContentCallBackCroner chapterContentCallBackCroner;

    @Autowired
    private FutureCrawlerCroner futureCrawlerCroner;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private ByStanderWooniuSynCroner byStanderWooniuSynCroner;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BaiduBrother baiduBrother;

    @RequestMapping("/list")
    public List<String> list() {
        List<String> cmds  = new ArrayList<>();
        cmds.add("/sysn_brother_chapterid/{article_id}");
        cmds.add("/recrawl/{article_id}");
        cmds.add("/recrawlall");
        cmds.add("/bystandsyn/{article_id}");
        cmds.add("/baidubrother/{article_id}");
        return cmds;
    }

    /**
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/sysn_brother_chapterid/{article_id}",method= RequestMethod.GET, produces="application/json")
    public String sysnBrotherChapterId(@PathVariable("article_id") int articleId) {
        try{
            synBrotherChapterId.sysChapterId(articleId);
        }catch (Exception e){
            logger.warn("",e);
            return "ERROR";
        }
        return "OK";
    }

    @RequestMapping(value = "/recrawl/{article_id}",method= RequestMethod.GET, produces="application/json")
    public String reCrawlArticle(@PathVariable("article_id") int articleId) {
        try{
            logger.info("reCrawlArticle "+articleId);
            chapterContentCallBackCroner.recrawl(articleId);
        }catch (Exception e){
            logger.warn("",e);
            return "ERROR";
        }
        return "OK";
    }

    @RequestMapping(value = "/recrawlall",method= RequestMethod.GET, produces="application/json")
    public String recrawlAll(){
        logger.info("recrawlAll entry!");
        List<FutureCrawler> lst = futureCrawlerService.queryAllToBeCrawler();
        logger.info("queryAllToBeCrawler size  "+lst.size());

        lst.parallelStream().forEach(futureCrawler -> {
            futureCrawlerCroner.crawl(futureCrawler);
            /*if(futureCrawler.getCrawlerState().equals("F")){
                futureCrawlerCallBackCroner.callback(futureCrawler);
            }*/
        });
        logger.info("recrawlAll out");
        return "OK";
    }

    @RequestMapping(value = "/bystandsyn/{article_id}",method= RequestMethod.GET, produces="application/json")
    public String bystandsyn(@PathVariable("article_id") int articleId) {
        logger.info("bystandsyn entry!");
        byStanderWooniuSynCroner.checkAndSyn(articleId);
        logger.info("bystandsyn out");
        return "OK";
    }

    @RequestMapping(value = "/baidubrother/{article_id}",method= RequestMethod.GET, produces="application/json")
    public String baiduBrother(@PathVariable("article_id") int articleId) throws Exception {
        logger.info("baiduBrother entry!");
        Article article = articleService.selectByPrimary(articleId);
        SearchResultPage searchResultPage = baiduBrother.search(article.getTitle().trim());
        if(searchResultPage.getResultItems().size()>0){
            List<String> pureLst = baiduBrother.loadPureChpLst(articleId);
            searchResultPage.getResultItems().forEach(url->{
                try {
                    logger.info("select:"+url);
                    Pair pair = baiduBrother.analysisCssSelector(url, pureLst);
                    if(pair!=null){
                        logger.info(pair.getLeft()+"|"+pair.getRight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        logger.info("baiduBrother out");
        return "OK";
    }
}
