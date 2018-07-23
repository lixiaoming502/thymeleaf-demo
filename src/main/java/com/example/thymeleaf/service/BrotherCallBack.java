package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.FutureCrawler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/14.
 */
@Component
public class BrotherCallBack extends AbstractCallBack{

    private static Log logger = LogFactory.getLog(BrotherCallBack.class);

    @Autowired
    private BrotherChapterService brotherChapterService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private BrotherService brotherService;


    protected void callback_level1(int crawlerId) {
        //TODO:
    }

    @Override
    protected void callback_level3(int crawlerId) {

    }

    /**
     * get the content of a chapter
     * @param crawlerId
     */
    protected void callback_level2(int crawlerId) {
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        JSONArray jsonArray = parseToJsonArray(record);
        List<JSONObject> lst = parseToList(jsonArray);
        Brother exist = brotherService.queryByURL(record.getPageUrl());
        final Integer brotherId = exist.getBrotherId();
        Integer articleId = brotherService.selectByBrotherId(brotherId).getArticleId();
        int maxSeqId = brotherChapterService.getMaxSeqId(brotherId);
        for(JSONObject info:lst) {
            int seqId = info.getInteger("seqId");
            if(seqId>maxSeqId){
                BrotherChapter chapter = new BrotherChapter();
                chapter.setBrotherId(brotherId);
                chapter.setSeqId(seqId);
                String title = info.getString("chapTitle");
                chapter.setTitle(title);
                String url = info.getString("href");
                chapter.setUrl(url);
                chapter.setCollectFlag(0);
                int chapterId = findChapterId(title,articleId);
                chapter.setChapterId(chapterId);
                try{
                    brotherChapterService.addRecord(chapter);
                }catch (Exception e){
                    if(e instanceof org.springframework.dao.DuplicateKeyException){
                        logger.info("duplicate url:"+url+" brotherId:"+brotherId+" crawlerId "+crawlerId);
                        continue;
                    }else{
                        logger.warn("callback_level2-error:",e);
                        throw e;
                    }
                }
            }
        }
    }

    private int findChapterId(String brotherTitle,int articleId){
        //match chapter_id,according to title
        //select like
        String pureText = extractPureTxt(brotherTitle);
        int chapterId = chapterService.selectLike(pureText,articleId);
        return chapterId;

    }

    private String extractPureTxt(String brotherTitle) {
        //第040章一秒不想看到
        int idx1 = brotherTitle.indexOf("第");
        int idx2 = brotherTitle.indexOf("章");
        if(idx1>-1&&idx2>idx1){
            return brotherTitle.substring(idx2+1).trim();
        }else{
            return brotherTitle;
        }
    }

}
