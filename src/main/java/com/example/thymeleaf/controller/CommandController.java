package com.example.thymeleaf.controller;

import com.example.thymeleaf.cron.BrotherCroner;
import com.example.thymeleaf.cron.ByStanderWooniuSynCroner;
import com.example.thymeleaf.cron.ChapterContentCallBackCroner;
import com.example.thymeleaf.cron.FutureCrawlerCroner;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.DomainCssSelectorGuess;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.NearTextService;
import com.example.thymeleaf.service.SynBrotherChapterId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private BrotherCroner brotherCroner;

    @Autowired
    private DomainCssSelectorGuess domainCssSelectorGuess;

    @Autowired
    private NearTextService nearTextService;


    @RequestMapping("/list")
    public List<String> list() {
        List<String> cmds  = new ArrayList<>();
        cmds.add("/sysn_brother_chapterid/{article_id}");
        cmds.add("/recrawl/{article_id}");
        cmds.add("/recrawlall");
        cmds.add("/bystandsyn/{article_id}");
        cmds.add("/baidubrother/{article_id}");
        cmds.add("/domain/guess/{domain_id}/{page_level}");
        cmds.add("/near/{article_id}/{nearLevel}");
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

    /**
     *
     * @param articleId
     * @return Map key:url Value:level2_css_selector,如"http://www.biquge.com.tw/0_65/":"#list > dl"
     * @throws Exception
     */
    @RequestMapping(value = "/baidubrother/{article_id}",method= RequestMethod.GET, produces="application/json")
    public Map baiduBrother(@PathVariable("article_id") int articleId) throws Exception {
        logger.info("baiduBrother entry!");
        Map retMap = brotherCroner.baiduBrother(articleId);
        logger.info("baiduBrother out");
        return retMap;
    }

    @RequestMapping(value = "/domain/guess/{domain_id}/{page_level}",method= RequestMethod.GET, produces="application/json")
    public String guessDomain(@PathVariable("domain_id") int domainId,@PathVariable("page_level") int pageLevel) throws Exception {
        logger.info("guessDomain entry!");
        String ret = domainCssSelectorGuess.guess2(domainId,pageLevel);
        logger.info("guessDomain out");
        return ret;
    }

    @RequestMapping(value = "/near/{article_id}/{near_level}",method= RequestMethod.GET, produces="application/json")
    public String near(@PathVariable("article_id") int articleId,@PathVariable("near_level") int nearLevel) throws Exception {
        logger.info("near entry!");
        if(nearLevel==0){
            nearTextService.genArticleNear(articleId);
        }else if(nearLevel==1){
            nearTextService.replaceOnline(articleId);
        }else if(nearLevel==2){
            nearTextService.genAndReplace(articleId);
        }
        logger.info("near out");
        return "OK";
    }
}
