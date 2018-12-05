package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.FutureCrawler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.similarity.JaccardSimilarity;
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

    @Autowired
    private SynBrotherChapterId synBrotherChapterId;


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
        //List<Chapter> chapterList = chapterService.queryByArticleId(articleId);
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
                int chapterId = synBrotherChapterId.findChapterId(title,articleId,seqId);
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

    private int findChapterId(String brotherTitle,int articleId,int seqId){
        String pureText = extractPureTxt(brotherTitle);
        //先根据seqId比较，文本相似度
        Chapter chapter = chapterService.queryByArticleIdSeqId(articleId,seqId);
        if(chapter!=null){
            String chpPureTitle = extractPureTxt(chapter.getTitle());
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            Double e = jaccardSimilarity.apply(pureText,chpPureTitle);
            logger.info("brotherTitle:"+brotherTitle+"check "+pureText+"->"+chpPureTitle);
            if(e>0.9){
                return chapter.getId();
            }
        }
        //match chapter_id,according to title
        //select like
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
