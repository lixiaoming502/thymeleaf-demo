package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.WooniuSynId;
import com.example.thymeleaf.service.ArticleService;
import com.example.thymeleaf.service.ChapterService;
import com.example.thymeleaf.service.NearTextService;
import com.example.thymeleaf.service.WooniuSynIdService;
import com.example.thymeleaf.util.SqlHelper;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
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
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixiaoming on 2018/7/5.
 */
@Component
public class WooniuSynCroner {

    private static Log logger = LogFactory.getLog(WooniuSynCroner.class);

    @Autowired
    private WooniuSynIdService wooniuSynIdService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NearTextService nearTextService;

    public void work(){
        logger.info("WooniuSynCroner start");
        List<WooniuSynId> lst = wooniuSynIdService.getToBeSyns();
        int toBeSynLen = lst.size()>5?5:lst.size();
        ArrayList<FutureTask> taskList = new ArrayList<>();
        logger.info("toBeSynLen:"+toBeSynLen);
        for(int i=0;i<toBeSynLen;i++){
            int finalI = i;
            FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
                syn(lst.get(finalI));
                return 0;
            });
            new Thread(futureTask).start();
            taskList.add(futureTask);
        }
        for(FutureTask futureTask:taskList){
            try {
                Object ret = futureTask.get(5, TimeUnit.MINUTES);
                logger.info("ret:"+ret);
            } catch (Exception e) {
                logger.warn("",e);
            }
        }
    }

    private boolean articleHealth(int articleId){
        List<Chapter> lst = chapterService.selectAllByArticleId(articleId);
        HashSet<String> md5 = new HashSet<String>();
        for(Chapter chapter:lst){
            if(CollectionUtils.isEmpty(lst)){
                logger.info("article_chapter is empty");
                return false;
            }
            String localUrl = chapter.getLocalUrl();
            if(StringUtils.isEmpty(localUrl)||!localUrl.startsWith("data")){
                return false;
            }
            File file = new File(localUrl);
            if(file.length() < 10){
                return false;
            }
            try {
                String content = FileUtils.readFileToString(file, "utf-8");
                String md5Hex = DigestUtils.md5Hex(content);
                if(md5.contains(md5Hex)){
                    logger.warn("md5 duplicate!");
                    return false;
                }else{
                    md5.add(md5Hex);
                }
            } catch (IOException e) {
                logger.warn("",e);
                return false;
            }
        }
        return true;
    }

    private void syn(WooniuSynId toBeSyn) {
        Integer articleId = toBeSyn.getArticleId();
        logger.info("syn "+articleId);
        Integer pos = toBeSyn.getPos();
        try{
            if(pos==null||pos==0){
                if(!articleHealth(articleId)){
                    logger.warn("article "+articleId+" health check false!!!");
                    wooniuSynIdService.updateError(toBeSyn);
                    return;
                }
                synArticle(articleId);
                pos=-1;
                wooniuSynIdService.updatePos(toBeSyn,pos);
            }
            List<Chapter> lst = chapterService.selectByArticleIdSeqId(articleId, pos);
            if(CollectionUtils.isEmpty(lst)){
                logger.info("update to be complete");
                wooniuSynIdService.updateComplete(toBeSyn);
                logger.info("add near service task");
                nearTextService.genAndReplace(articleId);
            }else{
                for(Chapter chapter:lst){
                    logger.info("syn seq_id:"+chapter.getSeqId());
                    String localUrl = chapter.getLocalUrl();
                    if(StringUtils.isEmpty(localUrl)||!localUrl.startsWith("data")){
                        wooniuSynIdService.updateError(toBeSyn);
                        throw new RuntimeException("localUrl非法,"+localUrl);
                    }
                    upFile(localUrl);
                    sysnChapter(chapter);
                    wooniuSynIdService.updatePos(toBeSyn,chapter.getSeqId());
                }
            }
        }catch (Exception e){
            logger.warn("",e);
        }
    }

    /**
     * 上传文件
     * @param localUrl
     */
    private void upFile(String localUrl) {
        String uploadURL = "http://bquant.xyz/tools/fileup1.php";
        File file = new File(localUrl);
        HttpRequest httpRequest = HttpRequest
                .post(uploadURL).form("upfile",file);
        HttpResponse httpResponse = httpRequest.send();
        if(httpResponse.statusCode()!=200){
            throw new RuntimeException("上传文件失败！"+localUrl);
        }
    }

    private void sysnChapter(Chapter chapter) throws Exception {
        //插入t_chapter记录
        //INSERT INTO `t_chapter` (`id`, `artile_id`, `seq_id`, `title`, `url`, `local_url`, `collect_flag`, `update_time`)
        Map<String, Object> params = new HashMap<>();
        params.put("id",chapter.getId());
        params.put("artile_id",chapter.getArtileId());
        params.put("seq_id",chapter.getSeqId());
        params.put("title",chapter.getTitle());
        params.put("url",chapter.getUrl());
        params.put("local_url",chapter.getLocalUrl());
        params.put("collect_flag",chapter.getCollectFlag());
        params.put("update_time",chapter.getUpdateTime());

        String sql = SqlHelper.insertTable("t_chapter", params);
        String result = SqlHelper.proxySql(sql);
        if(result.contains("Duplicate")){
            Map<String,Object> setMap = new HashMap<>();
            Map<String,Object> whereMap = new HashMap<>();
            setMap.put("local_url",chapter.getLocalUrl());
            whereMap.put("id",chapter.getId());
            sql = SqlHelper.updateTable("t_chapter",setMap,whereMap);
            logger.info("update-sql:"+sql);
            result = SqlHelper.proxySql(sql);
            logger.info("update-result:"+result);
        }else if(result.contains("error")){
            throw new RuntimeException("插入数据异常，chapter_id "+chapter.getId()+" remote result:"+result);
        }
    }

    private void synArticle(Integer articleId) throws Exception {
        //插入t_article对应记录

        //INSERT INTO `t_article` (`id`, `comment`, `title`, `seed_id`, `category_id`, `status`, `click_times`, `img`, `author`, `modify_date`)

        Article article = articleService.selectByPrimary(articleId);
        Map<String, Object> params = new HashMap<>();
        params.put("id",article.getId());
        params.put("comment",article.getComment());
        params.put("title",article.getTitle());
        params.put("seed_id",article.getSeedId());
        params.put("category_id",article.getCategoryId());
        params.put("status",article.getStatus());
        params.put("click_times",article.getClickTimes());
        params.put("img",article.getImg());
        params.put("author",article.getAuthor());
        params.put("modify_date",article.getModifyDate());
        String sql = SqlHelper.insertTable("t_article", params);
        String result = SqlHelper.proxySql(sql);
        if(result.contains("error")&&!result.contains("Duplicate")){
            throw new RuntimeException("插入数据异常，文章ID "+article+" remote result:"+result);
        }
    }


}
