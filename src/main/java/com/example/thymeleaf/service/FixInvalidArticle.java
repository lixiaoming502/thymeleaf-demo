package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.util.EasyMap;
import com.example.thymeleaf.util.SqlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lixiaoming on 2018/7/6.
 */
@Service
public class FixInvalidArticle {
    private static Log logger = LogFactory.getLog(FixInvalidArticle.class);

    @Autowired
    private SearchService biquGSearchService;

    public void work() throws Exception {
        String sql = "select * FROM `t_article` WHERE status=2 order by id";
        String result = SqlHelper.proxySql(sql);
        JSONArray jsonArray = JSONArray.parseArray(result);
        for(Object jsonObject:jsonArray){
            logger.debug("AAA:"+jsonObject);
            JSONObject element  = (JSONObject)jsonObject;
            String title  = element.getString("title");
            logger.debug(title);
            String replaceUrl = biquGSearchService.search(title);
            int articleId = element.getInteger("id");
            if(replaceUrl!=null){
                updateUrl(articleId,replaceUrl);
            }else{
                logger.info("get url null,title ["+title+"]");
            }
        }
    }

    private void updateUrl(int articleId, String replaceUrl)  {
        String tableName = "t_article";
        Map<String, Object> setMap = EasyMap.toMap("url",replaceUrl,"status",3);
        Map<String, Object> whereMap = EasyMap.toMap("id",articleId);
        String updatSql = SqlHelper.updateTable(tableName,setMap,whereMap);
        logger.info("result:"+updatSql);
        String ret = null;
        try {
            ret = SqlHelper.proxySql(updatSql);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        logger.info("ret:"+ret);
    }



    public static void main(String[] args) throws Exception {
        FixInvalidArticle fixInvalidArticle = new FixInvalidArticle();
        fixInvalidArticle.work();
    }
}
