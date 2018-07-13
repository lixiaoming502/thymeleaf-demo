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


    public boolean parse(int level, int crawlerId, Integer domainId, String url){
        this.domainId = domainId;
        switch (level){
            case 1:
                return praseLevel1(crawlerId,url);
            case 2:
                return parseLevel2(crawlerId,url);
        }
        return false;
    }

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
            return -1;
        }
        return Integer.parseInt(numbers);
    }

    protected boolean parseLevel2(int crawlerId, String url, String cssQuery,String rootURL) {
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            String respone = (String) future.getRespone();
            Document doc = Jsoup.parse(respone);

            Elements items = doc.select(cssQuery);
            JSONArray jsonArray = new JSONArray();
            for (Element item : items) {
                String href  = item.attr("href");
                if(href.startsWith("#")){
                    continue;
                }
                String title = item.text();
                int seqId = extractSeqId(title);
                if(seqId<0){
                    if(title.startsWith("序章")){
                        seqId=0;
                    }else{
                        continue;
                    }
                }
                String chapTitle = extractChapTitle(title);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("href",rootURL+href);
                jsonObject.put("seqId",seqId);
                jsonObject.put("chapTitle",chapTitle);
                jsonArray.add(jsonObject);
            }
            try {
                futureCrawlerService.finish(crawlerId,"F",jsonArray.toJSONString());
                return true;
            } catch (IOException e) {
                logger.warn("",e);
                return false;
            }
        }
        return false;
    }
}
