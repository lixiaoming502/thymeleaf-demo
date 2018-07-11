package com.example.thymeleaf.service;

import jodd.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiaoming on 2018/7/5.
 */
@Service
public class MyPan {

    private static Log logger = LogFactory.getLog(MyPan.class);

    @Autowired
    private JoddHttp joddHttp;

    private String baseUrl = "http://020mk.cn/tools/mypan.php";
    private String token = "ZMfW9rcb1NAag3l2";

    public boolean put(String key,String value){
        Map queryMap = new HashMap();
        queryMap.put("action","put");
        queryMap.put("token",token);
        queryMap.put("key",key);
        queryMap.put("content",value);
        HttpResponse response = joddHttp.sendBrowserByPost(baseUrl, baseUrl,queryMap);
        final String body = response.body();
        boolean ret = body.equals("1");
        if(!ret){
            logger.info(body);
        }
        return ret;
    }

    public String get(String key){
        Map queryMap = new HashMap();
        queryMap.put("action","get");
        queryMap.put("token",token);
        queryMap.put("key",key);
        HttpResponse response = joddHttp.sendBrowserByPost(baseUrl, baseUrl,queryMap);
        final String body = response.body();
        if(body.equals("EMPTY")){
            return null;
        }else{
            return body;
        }
    }
    public static void main(String[] args) throws IOException {
        MyPan myPan = new MyPan();
        myPan.joddHttp = new JoddHttp();
        final String content = myPan.get("https://m.xiaoshuoli.com/ph/week_2.html");
        String u8 = new String(content.getBytes("iso8859-1"), "UTF8");
        System.out.println(u8);
    }
}
