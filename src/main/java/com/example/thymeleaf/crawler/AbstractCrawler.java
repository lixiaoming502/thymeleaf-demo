package com.example.thymeleaf.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.selfdrive.DefaultSelfDriver;
import com.example.thymeleaf.selfdrive.DriverFuture;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiaoming on 2018/6/29.
 */
public abstract class AbstractCrawler implements Crawler {

    private static Logger logger = LoggerFactory.getLogger(AbstractCrawler.class);

    @Autowired
    protected DefaultSelfDriver selfDriver;

    @Autowired
    protected FutureCrawlerService futureCrawlerService;

    @Autowired
    protected FuturePageLoaderService futurePageLoaderService;

    public Integer domainId;

    public String level3FilterWords;

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

    public String filterWords(String content,String words){
        return content.replaceAll(words,"蜗牛小说网wap.wooniu.xyz");
    }

    public boolean parse(int level, int crawlerId, Integer domainId, String url){
        this.domainId = domainId;
        switch (level){
            case 1:
                return praseLevel1(crawlerId,url);
            case 2:
                return parseLevel2(crawlerId,url);
            case 3:
                return parseLevel3(crawlerId,url);
        }
        return false;
    }

    protected abstract boolean parseLevel3(int crawlerId, String url);

    protected abstract boolean parseLevel2(int crawlerId, String url);

    protected abstract boolean praseLevel1(int crawlerId, String url);


    protected DriverFuture createDriverFuture(int crawlerId, String url) {
        DriverFuture future = new DriverFuture(crawlerId,domainId,url);
        selfDriver.fetch(future);
        return future;
    }


    /**
     * 判断是否有页面待抓取
     * @param crawlerId
     * @return
     */
    protected boolean hasCrawlerPageWaiting(int crawlerId) {
        return futurePageLoaderService.isCrawlerPageWaiting(crawlerId);
    }


    protected String extractChapTitle(String title) {
        int index = title.indexOf(" ");
        return title.substring(index+1);
    }

    protected int extractSeqId(String title) {
        final String numbers = AppUtils.getNumbers(title);
        if(StringUtils.isEmpty(numbers)){
            logger.info("extractSeqId failed title:"+title);
            return -1;
        }
        return Integer.parseInt(numbers);
    }

    protected boolean parseLevel3(int crawlerId, String url, String cssQuery) {
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            if(future.getStatusCode()==200){
                String respone = (String) future.getRespone();
                Document doc = Jsoup.parse(respone);

                Elements items = doc.select(cssQuery);
                Element content = items.get(0);
                String htmlContent = content.html();
                htmlContent = sensitiveWordsFilter(htmlContent);
                logger.info(" html length "+htmlContent.length());
                if(level3FilterWords!=null){
                    htmlContent = filterWords(htmlContent,level3FilterWords);
                }
                return markedFinished(crawlerId, htmlContent);
            }else{
                return updateCrawlerResponse(crawlerId, "E", "");
            }
        }
        return false;
    }

    protected abstract String sensitiveWordsFilter(String htmlContent);

    private boolean markedFinished(int crawlerId, String htmlContent) {
        try {
            futureCrawlerService.finish(crawlerId,"F",htmlContent);
            if(htmlContent.trim().length()==0){
                //throw new IllegalStateException("htmlContent length is null");
                logger.info("htmlContent length is null crawlerId "+crawlerId);
            }
            return true;
        } catch (Exception e) {
            logger.warn("",e);
            return false;
        }
    }

    protected boolean parseLevel2(int crawlerId, String url, String cssQuery,String rootURL) {
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            if(future.getStatusCode()==200){
                String respone = (String) future.getRespone();
                Document doc = Jsoup.parse(respone);

                Elements items = doc.select(cssQuery);
                JSONArray jsonArray = new JSONArray();
                int seqId = 0;
                for (Element item : items) {
                    String href  = item.attr("href");
                    if(href.startsWith("#")){
                        continue;
                    }
                    if(!isMatch(href)){
                        logger.info("unmatched ["+href+"]");
                        continue;
                    }
                    seqId++;
                    String title = item.text();
                    String chapTitle = extractChapTitle(title);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("href",rootURL+href);
                    jsonObject.put("seqId",seqId);
                    jsonObject.put("chapTitle",chapTitle);
                    jsonArray.add(jsonObject);
                }
                return markedFinished(crawlerId, jsonArray.toJSONString());
            }else{
                return updateCrawlerResponse(crawlerId, "E", "");
            }
        }
        return false;
    }

    protected abstract boolean isMatch(String content);


    protected boolean updateCrawlerResponse(int crawlerId, String crawlerState,String response) {
        try {
            logger.info("updateCrawlerResponse,crawler_id,"+crawlerId);
            futureCrawlerService.finish(crawlerId,crawlerState,response);
            return true;
        } catch (IOException e) {
            logger.warn("",e);
            return false;
        }
    }
}
