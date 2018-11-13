package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.model.WooniuSynId;
import com.example.thymeleaf.service.ChapterService;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.WooniuSynIdService;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixiaoming on 2018/11/8.
 */
@Component
public class ByStanderWooniuSynCroner {

    private static Log logger = LogFactory.getLog(ByStanderWooniuSynCroner.class);

    private static Map<Integer,Long> articleIDMap = new ConcurrentHashMap<>();

    private static final long  TIME_OUT = 10*60*1000;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @Autowired
    private WooniuSynIdService wooniuSynIdService;

    ByStanderWooniuSynCroner(){
        Map obj = (Map)AppUtils.readObject("ByStanderWooniuSynCroner");
        if(obj!=null){
            articleIDMap.putAll(obj);
        }
    }

    public void work(){
        logger.info("ByStanderWooniuSynCroner entry");
        Set<Map.Entry<Integer, Long>> entries = articleIDMap.entrySet();
        for(Map.Entry<Integer, Long> entry:entries){
            final Integer articleID = entry.getKey();
            logger.info("articleID."+articleID);
            Long tag = articleIDMap.get(articleID);
            long cur = System.currentTimeMillis();
            if(cur-tag>TIME_OUT){
                checkAndSyn(articleID);
            }
        }
        logger.info("ByStanderWooniuSynCroner end");
    }

    public void checkAndSyn(Integer articleID) {
        logger.info("checkAndSyn "+articleID);
        if(wooniuSynIdService.selectByArticleId(articleID)!=null){
            logger.info("wooniuSynId is not null,so not need to check!remove and return!");
            articleIDMap.remove(articleID);
            return;
        }
        if(!articleIDMap.containsKey(articleID)){
           synArticleIDMap(articleID);
        }
        if(articleHealth(articleID)){
            logger.info("add article_id to wooniuSyn"+articleID);
            WooniuSynId wooniuSynId = new WooniuSynId() ;
            wooniuSynId.setArticleId(articleID);
            wooniuSynId.setStatus(0);
            wooniuSynId.setPos(0);
            wooniuSynIdService.add(wooniuSynId);
            articleIDMap.remove(articleID);
        }
    }

    private boolean articleHealth(int articleId){
        List<Chapter> lst = chapterService.selectAllByArticleId(articleId);
        HashMap<String,Integer> md5 = new HashMap<>();
        boolean ret = true;
        int contentEmptyCount = 0,md5DupCount=0;
        for(Chapter chapter:lst){
            if(CollectionUtils.isEmpty(lst)){
                logger.info("article_chapter is empty");
                return false;
            }
            String localUrl = chapter.getLocalUrl();
            if(StringUtils.isEmpty(localUrl)||!localUrl.startsWith("data")){
                tryRecrawl(articleId,lst);
                return false;
            }
            File file = new File(localUrl);
            if(file.length() < 10){
                logger.info("localUrl file length <10 ,chapter_id_ "+chapter.getId());
                FutureCrawler record = futureCrawlerService.queryByRefId(chapter.getId());
                if(record==null){
                    return false;
                }
                if(System.currentTimeMillis()-articleIDMap.get(articleId)>10*TIME_OUT){
                    //如果超过100分钟，仍旧爬取失败，则删除之
                    //删除后面重复的
                    chapterService.delete(chapter.getId());
                    contentEmptyCount++;
                }else{
                    logger.info("try recrawlFutureCrawler");
                    recrawlFutureCrawler(record, record.getId());
                }
                ret = false;
                continue;
            }
            try {
                String content = FileUtils.readFileToString(file, "utf-8");
                String md5Hex = DigestUtils.md5Hex(content);
                if(md5.containsKey(md5Hex)){
                    final Integer last_chapter_id = md5.get(md5Hex);
                    logger.warn("md5 duplicate!"+ last_chapter_id +"->"+chapter.getId());
                    //如果title重复，则删除大的一个
                    if(duplicateTitleDel(last_chapter_id,chapter)){
                        logger.info("duplicateTitleDel "+chapter.getId());
                        continue;
                    }else{
                        //删除后面重复的
                        chapterService.delete(chapter.getId());
                        md5DupCount++;
                    }
                    ret = false;
                }else{
                    md5.put(md5Hex,chapter.getId());
                }
            } catch (IOException e) {
                logger.warn("",e);
                return false;
            }
        }
        logger.info("articleID "+articleId+" md5DupCount "+md5DupCount+" contentEmptyCount "+contentEmptyCount);
        return ret;
    }

    /**
     * 如果last_chapter_id的title和给的的title相等，删除chapter,则返回真
     * @param lastChapterId
     * @param curChapter
     * @return
     */
    private boolean duplicateTitleDel(Integer lastChapterId, Chapter curChapter) {
        Chapter lastChapter = chapterService.queryById(lastChapterId);
        if(lastChapter.getTitle().trim().equals(curChapter.getTitle().trim())){
            chapterService.delete(curChapter.getId());
            return true;
        }
        return false;
    }

    private void tryRecrawl(int articleId, List<Chapter> chapterList) {
        logger.info("tryRecrawl "+articleId);
        List<Integer> refIds = new ArrayList<>();
        for(Chapter chapter:chapterList){
            String localUrl = chapter.getLocalUrl();
            if(StringUtils.isEmpty(localUrl)||!localUrl.startsWith("data")){
                refIds.add(chapter.getId());
            }
        }
        for(int chapterId:refIds){
            if(!recrawlChapter(chapterId)){
                return;
            }
        }
        articleIDMap.remove(articleId);
    }

    private boolean recrawlChapter(int chapterId){
        //根据ref_id，找到crawler_id，根据crawler_id
        FutureCrawler record = futureCrawlerService.queryByRefId(chapterId);
        if(record==null){
            return false;
        }

        final Integer crawlerId = record.getId();
        if(record.getCrawlerState().equals("E")){
            recrawlFutureCrawler(record, crawlerId);
        }
        return true;
    }

    private void recrawlFutureCrawler(FutureCrawler record, Integer crawlerId) {
        List<FuturePageLoader> pages = futurePageLoaderService.getByCrawlerId(crawlerId);
        for(FuturePageLoader page:pages){
            if(page.getLoaderState().equals("E")){
                page.setStateCode(0);
                page.setLoaderState("A");
                page.setResponse("");
                futurePageLoaderService.update(page);
            }else if(page.getLoaderState().equals("F")){
                logger.info("page not need reset!");
            }
        }

        record.setCrawlerState("A");
        record.setResponse("");
        futureCrawlerService.update(record);
    }

    public void synArticleIDMap(int article_id){
        logger.info("synArticleID "+article_id);
        long tag = System.currentTimeMillis();
        articleIDMap.put(article_id,tag);
        AppUtils.writeObject(articleIDMap,"ByStanderWooniuSynCroner");
    }
}
