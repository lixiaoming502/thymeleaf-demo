package com.example.thymeleaf.cron;

import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.*;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
import com.example.thymeleaf.vo.SearchResultPage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.*;

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

    @Autowired
    private BaiduBrother baiduBrother;

    @Autowired
    private DomainCssSelectorService domainCssSelectorService;

    private static final int MAXLEN = 20;

    //@Scheduled(fixedDelay = 3000)
    public void work(){
        logger.info("BrotherCroner entry");
        findArticleBrother();
        findBrotherChapterList();
        logger.info("BrotherCroner end");
    }

    private void findBrotherChapterList() {
        List<Brother> lst = brotherService.selectByUpdateTurns(0);
        lst.stream().forEach(brother -> {
            try {
                final String brotherUrl = brother.getBrotherUrl();
                String domainName = AppUtils.extraDomain(brotherUrl);
                FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByActiveDomain(domainName);
                if(cfg==null){
                    logger.warn("can't get domainName cfg,["+domainName+"] borther_id ["+brother.getBrotherId()+"]");
                    return;
                }
                Integer domainId = cfg.getId();
                if(futureCrawlerService.getMaxLen(domainId)>MAXLEN){
                    logger.info("maxLen exceed!domainId "+domainId);
                    return;
                }
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

    public Map<String,String> baiduBrother(int articleId) throws Exception {
        Article article = articleService.selectByPrimary(articleId);
        SearchResultPage searchResultPage = baiduBrother.search(article.getTitle().trim());
        HashMap<String,String> retMap  = new HashMap<>();
        if(searchResultPage.getResultItems().size()>0){
            List<String> pureLst = baiduBrother.loadPureChpLst(articleId);
            HashSet<String> locations = new HashSet<>();
            searchResultPage.getResultItems().forEach(url->{
                try {
                    logger.info("select:"+url);
                    Pair pair = baiduBrother.analysisCssSelector(url, pureLst,locations);
                    if(pair!=null){
                        logger.info(pair.getLeft()+"|"+pair.getRight());
                        retMap.put((String) pair.getLeft(),(String) pair.getRight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        processBaiduBrotherResult(retMap,articleId);
        return retMap;
    }

    private void processBaiduBrotherResult(Map<String,String> brotherMap,int articleId) throws MalformedURLException {
        //外部传入要爬取的文章，应该放在一个缓存中
        //使用baidu爬取到所有兄弟到一个map，排除主域名（比如说xiaoshuoli.com）和重复的域名（如biquge.com,m.biquge.com）-》全部插入t_brother
        //插入t_brother
        //查询t_future_crawler_cfg是否有该域名
        //如果没有，插入t_future_crawler_cfg该域名，enable设置为A
        Set<Map.Entry<String, String>> sets = brotherMap.entrySet();
        for(Map.Entry<String, String> entry:sets){
            final String brotherUrl = entry.getKey();

            Brother brother = new Brother();
            brother.setArticleId(articleId);
            brother.setBrotherUrl(brotherUrl);
            brother.setUpdateTurns(0);
            logger.info("add brother "+brotherUrl);
            Brother exist = brotherService.queryByURL(brotherUrl);
            if(exist==null){
                brotherService.insert(brother);
            }

            String domainName = AppUtils.extraDomain(brotherUrl);
            FutureCrawlerCfg futureCrawlerCfg = futureCrawlerCfgService.queryByDomain(domainName);
            if(futureCrawlerCfg==null){
                futureCrawlerCfg = new FutureCrawlerCfg();
                futureCrawlerCfg.setEnable("A");
                futureCrawlerCfg.setDomainName(domainName);
                logger.info("add futureCrawlerCfg ");
                futureCrawlerCfg.setGap(5);
                futureCrawlerCfg.setDefaultParserSeed(27);
                futureCrawlerCfg.setCharset("utf8");
                futureCrawlerCfgService.insert(futureCrawlerCfg);

                futureCrawlerCfg = futureCrawlerCfgService.queryByDomain(domainName);

                //插入t_domain_css_selector
                DomainCssSelector domainCssSelector = new DomainCssSelector();
                domainCssSelector.setDomainId(futureCrawlerCfg.getId());
                domainCssSelector.setLevel2Selector(entry.getValue());
                JSONObject rules = new JSONObject();
                rules.put("l3_preUrl",brotherUrl);
                domainCssSelector.setExtraRule(rules.toJSONString());
                logger.info("add domain_css_selector");
                domainCssSelectorService.insert(domainCssSelector);
            }
        }
    }

    private void findArticleBrother() {
        List<Article> articles = articleService.getNoBrothers();
        articles.stream().forEach(article->{
            String title = article.getTitle();
            logger.info("title:["+title+"] id "+article.getId()+"]");
            List<String> urls = null;
            try{
                urls = searchService.searchAll(title);
            }catch (Exception e){
                logger.warn("",e);
            }
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
