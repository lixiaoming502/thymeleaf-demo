package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.FutureCrawler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by lixiaoming on 2018/7/12.
 */
@Component
public class ArticleCallBack {

    private static Log logger = LogFactory.getLog(ArticleCallBack.class);


    @Autowired
    private ArticleService articleService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void callback(int level,int crawlerId){
        switch (level){
            case 1:
                callback_level1(crawlerId);
                break;
            case 2:
                callback_level2(crawlerId);
                break;
        }
    }

    private void callback_level1(int crawlerId) {
        logger.info("callback_level1 entry,crawlerId "+crawlerId);
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        JSONArray jsonArray = parseToJsonArray(record);
        for(Object obj:jsonArray){
            JSONObject info = (JSONObject)obj;
            Article article = new Article();
            String comment = info.getString("review");
            if(comment.length()>500){
                comment = comment.substring(0,500);
            }
            article.setComment(comment);
            String title = info.getString("title");
            article.setTitle(title);
            String listurl  = info.getString("list_url");
            article.setListUrl(listurl);
            String infourl  = info.getString("info_url");
            article.setInfoUrl(infourl);
            article.setSeedId(record.getSeedId());
            int categoryId = info.getInteger("sort");
            article.setCategoryId(categoryId);
            int status = info.getInteger("status");
            article.setStatus(status);
            String img = info.getString("img");
            article.setImg(img);
            String author  = info.getString("author");
            article.setAuthor(author);
            Article exist = articleService.queryByListURL(listurl);
            if(exist==null){
                articleService.addRecord(article);
            }else{
                article.setId(exist.getId());
                articleService.update(article);
            }

        }
    }

    private void callback_level2(int crawlerId) {
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        JSONArray jsonArray = parseToJsonArray(record);
        Article exist = articleService.queryByListURL(record.getPageUrl());
        final Integer artileId = exist.getId();
        int maxSeqId = chapterService.getMaxSeqId(artileId);

        //JSONArray排序
        List<JSONObject> lst = jsonArray.toJavaList(JSONObject.class);
        Collections.sort(lst, (o1, o2) -> {
            int seqId1 = o1.getInteger("seqId");
            int seqId2 = o2.getInteger("seqId");
            return seqId1-seqId2;
        });

        for(JSONObject info:lst) {
            int seqId = info.getInteger("seqId");
            if(seqId>maxSeqId){
                Chapter chapter = new Chapter();
                chapter.setArtileId(artileId);
                chapter.setSeqId(seqId);
                String title = info.getString("chapTitle");
                chapter.setTitle(title);
                String url = info.getString("href");
                chapter.setUrl(url);
                chapter.setCollectFlag(false);
                try{
                    chapterService.addRecord(chapter);
                }catch (Exception e){
                    if(e instanceof org.springframework.dao.DuplicateKeyException){
                        logger.info("duplicate url:"+url+" articleId:"+artileId+" crawlerId "+crawlerId);
                        continue;
                    }else{
                        logger.warn("callback_level2-error:",e);
                        throw e;
                    }
                }
            }
        }
    }

    private JSONArray parseToJsonArray(FutureCrawler record) {
        JSONArray jsonArray = null;
        try {
            String path = record.getResponse();
            String content  = FileUtils.readFileToString(new File(path),"utf8");
            jsonArray = JSONArray.parseArray(content);
        } catch (IOException e) {
            logger.warn(e);
        }
        return jsonArray;
    }
}
