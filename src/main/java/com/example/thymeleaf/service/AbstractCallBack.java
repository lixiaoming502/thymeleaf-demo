package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.FutureCrawler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by lixiaoming on 2018/7/14.
 */
public abstract class AbstractCallBack implements CallBack {

    private static Log logger = LogFactory.getLog(AbstractCallBack.class);

    @Autowired
    protected FutureCrawlerService futureCrawlerService;

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

    protected abstract void callback_level2(int crawlerId);

    protected abstract void callback_level1(int crawlerId);


    protected JSONArray parseToJsonArray(FutureCrawler record) {
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

    protected List<JSONObject> parseToList(JSONArray jsonArray) {
        List<JSONObject> lst = jsonArray.toJavaList(JSONObject.class);
        Collections.sort(lst, (o1, o2) -> {
            int seqId1 = o1.getInteger("seqId");
            int seqId2 = o2.getInteger("seqId");
            return seqId1-seqId2;
        });
        return lst;
    }

}
