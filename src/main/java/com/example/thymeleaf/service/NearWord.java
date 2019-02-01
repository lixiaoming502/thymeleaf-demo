package com.example.thymeleaf.service;

import jodd.http.HttpResponse;
import jodd.http.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixiaoming on 2018/12/6.
 */
@Service
public class NearWord {

    private static Log logger = LogFactory.getLog(NearTextService.class);

    @Autowired
    private JoddHttp joddHttp;

    final String BASE_URL = "http://www.ximizi.com/Fanyici_Results.php";

    public String getWord(String input) throws UnsupportedEncodingException {
        Map formMap = new HashMap();
        formMap.put("fanyici_key",input);
        formMap.put("chaxun"," 同义词查询/反义词查询 ");
        FutureTask<HttpResponse> futureTask = joddHttp.aSynSendBrowserByPost(BASE_URL, BASE_URL, formMap);
        HttpResponse response = null;
        try {
            response = futureTask.get(5, TimeUnit.MINUTES);
        } catch (Exception e) {
           logger.warn("",e);
           return null;
        }
        if(response.statusCode()== HttpStatus.HTTP_OK){
            byte[] bytes = response.bodyBytes();
            String html = new String(bytes,"utf-8");
            //div.crm_text_1 p
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("div.crm_text_1 p");
            String tag = "【同义词】：";
            for(Element element:elements){
                String txt = element.text();
                if(txt.contains(tag)&&txt.length()>tag.length()){
                    String pureTxt = txt.substring(tag.length());
                    String[] sa = pureTxt.split("、");
                    if(sa.length>0){
                        Random random = new Random();
                        int idx = random.nextInt(sa.length);
                        return sa[idx];
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        JoddHttp joddHttp = new JoddHttp();
        String url = "http://www.ximizi.com/Fanyici_Results.php";
        Map formMap = new HashMap();
        formMap.put("fanyici_key","11");
        formMap.put("chaxun"," 同义词查询/反义词查询 ");
        HttpResponse response = joddHttp.sendBrowserByPost(url, url, formMap);
        if(response.statusCode()== HttpStatus.HTTP_OK){
            byte[] bytes = response.bodyBytes();
            String html = new String(bytes,"utf-8");
            //div.crm_text_1 p
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("div.crm_text_1 p");
            String tag = "【同义词】：";
            for(Element element:elements){
                String txt = element.text();
                if(txt.contains(tag)&&txt.length()>tag.length()){
                    String pureTxt = txt.substring(tag.length());
                    String[] sa = pureTxt.split("、");
                    for(String s:sa){
                        System.out.println("A:"+s);
                    }
                }
            }
            System.out.println("done");
        }
    }
}
