package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.util.SqlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lixiaoming on 2018/7/11.
 */
@Component
public class ArticleUpdater {

    private static Log logger = LogFactory.getLog(ArticleUpdater.class);

    private static String QUERY_SQL ="SELECT * FROM `t_article` WHERE `status` = 0 and modify_date<'?' limit 10";

    public void work() throws Exception {
        String result = SqlHelper.proxySql(QUERY_SQL);
        JSONArray jsonArray = JSONArray.parseArray(result);
        for(Object jsonObject:jsonArray){
            logger.debug("AAA:"+jsonObject);
            JSONObject element  = (JSONObject)jsonObject;
            String title  = element.getString("title");
            logger.debug(title);
            //添加到种子里面，添加回调
        }
    }

}
