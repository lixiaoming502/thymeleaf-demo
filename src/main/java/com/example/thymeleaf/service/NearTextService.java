package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.NearTextMapper;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.NearText;
import com.example.thymeleaf.model.NearTextExample;
import com.example.thymeleaf.util.SqlHelper;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * Created by lixiaoming on 2018/12/14.
 */
@Service
public class NearTextService {

    private static Log logger = LogFactory.getLog(NearTextService.class);

    @Autowired
    private NearTextMapper nearTextMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private BaiduAI baiduAI;

    @Autowired
    private NearWord nearWord;

    private Executor executor = Executors.newSingleThreadExecutor();

    public void insert(NearText nearText){
        nearTextMapper.insert(nearText);
    }

    public void sequenceExecute(Runnable runnable){
        executor.execute(runnable);
    }

    public void genAndReplace(int articleId){
        final Runnable target = () -> {
            try {
                genArticleNear(articleId);
            } catch (UnsupportedEncodingException e) {
                logger.warn("", e);
            }
            try {
                replaceOnline(articleId);
            } catch (Exception e) {
                logger.warn("", e);
            }
        };
        sequenceExecute(target);
    }

    public List<NearText> select(int articleId){
        NearTextExample nearTextExample = new NearTextExample();
        NearTextExample.Criteria condition = nearTextExample.createCriteria();
        condition.andArticleIdEqualTo(articleId);
        condition.andIsOnlineEqualTo(false);
        List<NearText> lst = nearTextMapper.selectByExample(nearTextExample);
        return lst;
    }

    public void update(NearText nearText){
        nearTextMapper.updateByPrimaryKey(nearText);
    }

    public NearText select(int articleId,int textId){
        NearTextExample nearTextExample = new NearTextExample();
        NearTextExample.Criteria condition = nearTextExample.createCriteria();
        condition.andArticleIdEqualTo(articleId);
        condition.andTextIdEqualTo(textId);
        List<NearText> lst = nearTextMapper.selectByExample(nearTextExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }
        return null;
    }

    public void genArticleNear(int articleId) throws UnsupportedEncodingException {
        Article article = articleService.selectByPrimary(articleId);
        int textId = 0;
        logger.info("gen near:"+textId);
        String text = article.getComment();
        toNear(articleId, textId, text);
        List<Chapter> chapterList = chapterService.selectAllByArticleId(articleId);
        for(Chapter chapter:chapterList){
            textId = chapter.getId();
            logger.info("gen near:"+textId);
            text = chapter.getTitle();
            toNear(articleId, textId, text);
        }
    }

    private void toNear(int articleId, int textId, String origTxt) throws UnsupportedEncodingException {
        if(select(articleId,textId)==null){
            List<String> words = baiduAI.wordsTag(origTxt);
            if(CollectionUtils.isNotEmpty(words)){
                HashMap<String,String> result = Maps.newHashMap();
                for(String word:words){
                    if(!result.containsKey(word)){
                        String nearTxt = nearWord.getWord(word);
                        if(nearTxt!=null){
                            result.put(word,nearTxt);
                        }
                    }
                }
                if(result.size()>0){
                    NearText nearText = new NearText();
                    nearText.setArticleId(articleId);
                    nearText.setTextId(textId);
                    nearText.setOriginText(origTxt);
                    String nearTexts = createNearTexts(origTxt,result);
                    nearText.setNearText(nearTexts);
                    nearText.setIsOnline(false);
                    insert(nearText);
                }
            }
        }
    }

    private String createNearTexts(String comment, HashMap<String, String> result) {
        Set<String> keys = result.keySet();
        for(String key:keys){
            String value = result.get(key);
            logger.info(key+":"+value);
            comment = comment.replaceFirst(key,value);
        }
        return comment;
    }

    public void replaceOnline(int articleId) throws Exception {
        List<NearText> lst = select(articleId);
        for(NearText nearText:lst){
            logger.info("syn nearId:"+nearText.getNearId());
            Integer textId = nearText.getTextId();
            String nearStr = nearText.getNearText();
            Map<String,Object> setMap = new HashMap<>();
            Map<String,Object> whereMap = new HashMap<>();
            if(textId==0){
                setMap.put("comment",nearStr);
                whereMap.put("id",articleId);
                String sql = SqlHelper.updateTable("t_article",setMap,whereMap);
                logger.info("update-sql:"+sql);
                String result = SqlHelper.proxySql(sql);
                logger.info("update-result:"+result);
            }else{
                setMap.put("title",nearStr);
                whereMap.put("id",textId);
                String sql = SqlHelper.updateTable("t_chapter",setMap,whereMap);
                logger.info("update-sql:"+sql);
                String result = SqlHelper.proxySql(sql);
                logger.info("update-result:"+result);
            }
            nearText.setIsOnline(true);
            update(nearText);
        }
    }
}
