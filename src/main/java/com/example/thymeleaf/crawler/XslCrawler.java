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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class XslCrawler extends AbstractCrawler{

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "https://m.xiaoshuoli.com";

    static Map<String,Integer> sortMap = new HashMap<>();
    static {
        sortMap.put("言情小说",1);
        sortMap.put("仙侠修真",2);
        sortMap.put("都市小说",3);
        sortMap.put("历史小说",4);
        sortMap.put("玄幻小说",5);
        sortMap.put("网游小说",6);
        sortMap.put("竞技小说",7);
        sortMap.put("科幻小说",8);
        sortMap.put("其他小说",9);
        sortMap.put("精选小说",10);
    }

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

    public boolean parseLevel2(int crawlerId,String url){
        final String cssQuery = "#chapterlist a";
        return parseLevel2(crawlerId, url, cssQuery,baseUrl);
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
        //作者：许微笑
        int idx = author.indexOf("：");
        if(idx>0){
            author = author.replace("小说免费阅读","");
            author = author.substring(idx+1);
        }
        String sort = doc.select("p.sort").get(0).text();
        String review = doc.select("p.review").get(0).text();
        String infoUrl = future.getPageUrl();
        //body > div.synopsisArea > div > p:nth-child(4)
        String status = doc.select(".synopsisArea_detail p").get(2).text();
        int iStatus = 1;
        if(status.contains("连载中")){
            iStatus = 0;
        }
        JSONObject cpo = new JSONObject();
        cpo.put("list_url",baseUrl+chpUrl);
        cpo.put("img",img);
        cpo.put("title",title);
        cpo.put("author",author);
        cpo.put("sort",convertSort(sort));
        cpo.put("review",review);
        cpo.put("info_url",infoUrl);
        cpo.put("status",iStatus);
        return cpo;
    }

    private int convertSort(String sort) {
        //类别：都市小说
        int idx = sort.indexOf("：");
        String s =  sort.substring(idx+1).trim();
        final Integer integer = sortMap.get(s);
        if(integer==null){
            //throw new IllegalArgumentException(sort);
            logger.warn("undefined sort "+sort);
            return -1;
        }
        return integer;
    }

    private String correct(String title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    protected boolean isMatch(String content){
        //String content = "/i23739/6921515.html";
        Pattern pattern = Pattern.compile("/i\\d+/\\d+.html");
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
