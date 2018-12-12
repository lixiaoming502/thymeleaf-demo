package com.example.thymeleaf.service;

import jodd.http.HttpResponse;
import jodd.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiaoming on 2018/12/6.
 */
public class NearWord {

    public static void main(String[] args) throws UnsupportedEncodingException {
        JoddHttp joddHttp = new JoddHttp();
        String url = "http://www.ximizi.com/Fanyici_Results.php";
        Map formMap = new HashMap();
        formMap.put("fanyici_key","寸步不离");
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
                        System.out.println(s);
                    }
                }
            }
            System.out.println("done");
        }
    }
}
