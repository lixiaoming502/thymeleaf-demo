package com.example.thymeleaf.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.selfdrive.DriverFuture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class XslCrawler extends AbstractCrawler{

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "https://m.xiaoshuoli.com/";

    public boolean praseLevel1(int crawlerId,String url){
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            String respone = (String)future.getRespone();
            Document doc = Jsoup.parse(respone);
            Elements items = doc.select("div#main div.hot_sale");
            JSONArray jsonArray = new JSONArray();
            String crawlerState = "F";
            int error = 0;
            for(Element item:items){
                Element infoUrlE = item.select("a").get(0);
                String infoUrl  = infoUrlE.attr("href");
                logger.info("get infoUrl:"+infoUrl);
                String pageUrl = baseUrl+infoUrl;
                future = createDriverFuture(crawlerId,pageUrl);
                if(future.isDone()){
                    if( future.getStatusCode()==200){
                        JSONObject cpo = parseInfo(future);
                        logger.info("get cpo:"+cpo.toJSONString());
                        jsonArray.add(cpo);
                    }else{
                        logger.warn("set crawlerState E ,crawlerId:"+crawlerId);
                        crawlerState = "E";
                        ++error;
                    }
                }
            }
            if(jsonArray.size()+error!=items.size()){
                return false;
            }
            try {
                futureCrawlerService.finish(crawlerId,crawlerState,jsonArray.toJSONString());
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
        String author = doc.select("p.author").get(0).text();
        String sort = doc.select("p.sort").get(0).text();
        String review = doc.select("p.review").get(0).text();
        //author = correct(author);
        JSONObject cpo = new JSONObject();
        cpo.put("chpUrl",baseUrl+chpUrl);
        cpo.put("img",img);
        cpo.put("title",title);
        cpo.put("author",author);
        cpo.put("sort",sort);
        cpo.put("review",review);
        return cpo;
    }

    private String correct(String title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    public boolean parseLevel2(int crawlerId,String url){
        return false;
    }
}
