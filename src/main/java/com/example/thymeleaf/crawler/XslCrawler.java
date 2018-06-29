package com.example.thymeleaf.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.selfdrive.DefaultSelfDriver;
import com.example.thymeleaf.selfdrive.DriverFuture;
import com.example.thymeleaf.service.FutureCrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class XslCrawler implements Crawler{

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    @Autowired
    private DefaultSelfDriver selfDriver;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    private String domain = "m.xiaoshuoli.com";

    private String baseUrl = "https://m.xiaoshuoli.com/";

    public boolean parse(int level,int crawlerId,String url){
        switch (level){
            case 1:
                return praseLevel1(crawlerId,url);
            case 2:
                return parseLevel2(crawlerId,url);
        }
        return false;
    }

    public boolean praseLevel1(int crawlerId,String url){
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            String respone = (String)future.getRespone();
            Document doc = Jsoup.parse(respone);
            Elements items = doc.select("div#main div.hot_sale");
            JSONArray jsonArray = new JSONArray();
            for(Element item:items){
                Element infoUrlE = item.select("a").get(0);
                String infoUrl  = infoUrlE.attr("href");
                logger.info("get infoUrl:"+infoUrl);
                String pageUrl = baseUrl+infoUrl;
                future = createDriverFuture(crawlerId,pageUrl);
                if(future.isDone()){
                    JSONObject cpo = parseInfo(future);
                    logger.info("get cpo:"+cpo.toJSONString());
                    jsonArray.add(cpo);
                }
            }
            if(jsonArray.size()!=items.size()){
                return false;
            }
            try {
                futureCrawlerService.finish(crawlerId,jsonArray.toJSONString());
            } catch (IOException e) {
                logger.warn("",e);
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    private JSONObject parseInfo(DriverFuture future) {
        String respone;
        Document doc;
        respone = (String)future.getRespone();
        doc = Jsoup.parse(respone);
        String chpUrl = doc.select("div.list_1 a").get(0).attr("href");
        String img = doc.select(".synopsisArea_detail img").get(0).attr("src");
        String title = doc.select("h1.title").get(0).text();
        title = correct(title);
        String author = doc.select("p.author").get(0).text();
        JSONObject cpo = new JSONObject();
        cpo.put("chpUrl",baseUrl+chpUrl);
        cpo.put("img",img);
        cpo.put("title",title);
        cpo.put("author",author);
        return cpo;
    }

    private String correct(String title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    private DriverFuture createDriverFuture(int crawlerId, String url) {
        DriverFuture future = new DriverFuture(crawlerId,url);
        selfDriver.fetch(future);
        return future;
    }

    public String getDomain(){
        return domain;
    }

    public boolean parseLevel2(int crawlerId,String url){
        return false;
    }
}
