package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.util.AppUtils;
import com.example.thymeleaf.util.EasyMap;
import com.example.thymeleaf.util.SqlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by lixiaoming on 2018/7/11.
 */
@Component
public class ArticleUpdater {

    private static Log logger = LogFactory.getLog(ArticleUpdater.class);

    private static String QUERY_SQL ="select * FROM `t_article` WHERE `status` = 0 and modify_date<'?' limit 10";

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;


    public void work() throws Exception {
        final String currentDay = AppUtils.getCurrentDay();
        String sql = QUERY_SQL.replace("?", currentDay);
        String result = SqlHelper.proxySql(sql);
        JSONArray jsonArray = JSONArray.parseArray(result);
        for(Object jsonObject:jsonArray){
            logger.debug("AAA:"+jsonObject);
            JSONObject element  = (JSONObject)jsonObject;
            String title  = element.getString("title");
            String url  = element.getString("url");
            Integer seedId  = element.getInteger("seed_id");
            Integer id  = element.getInteger("id");
            logger.debug(title);
            //添加到种子里面，添加回调,https://www.zwdu.com/book/12432/
            //81zw.com
            /*if(url.contains("81zw.com")){
                url = url.replace("81zw.com","zwdu.com");
            }*/
            String domainName = AppUtils.extraDomain(url);
            FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomain(domainName);
            Integer domainId = cfg.getId();
            int level = 2;
            String crawlerState = "A";
            FutureCrawler futureCrawler = new FutureCrawler();
            futureCrawler.setCrawlerState(crawlerState);
            futureCrawler.setDomainId(domainId);
            futureCrawler.setPageUrl(url);
            futureCrawler.setCreateTime(new Date());
            futureCrawler.setSeedId(seedId);
            futureCrawler.setLevel(level);
            futureCrawlerService.addNew(futureCrawler);
            updateModifyDate(id,url,currentDay);
        }
    }

    private void updateModifyDate(Integer id, String url,String currentDay) {
        String tableName = "t_article";
        Map<String, Object> setMap = EasyMap.toMap("modify_date",currentDay,"url",url);
        Map<String, Object> whereMap = EasyMap.toMap("id",id);
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

}
