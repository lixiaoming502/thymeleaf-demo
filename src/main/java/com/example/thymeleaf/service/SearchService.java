package com.example.thymeleaf.service;

import jodd.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2018/7/5.
 */
@Service
public class SearchService {

    private static Log logger = LogFactory.getLog(SearchService.class);

    @Autowired
    private JoddHttp joddHttp;

    private String baseUrl = "https://www.xxbiquge.com/search_xbq.php?keyword=";
    private String wqbaseUrl = "http://www.wuqingshui.com/search_xbq?search_xbq=";
    private String yqbaseUrl = "http://www.yqxs.cc/s.php?ie=gbk&q=";
    private String kkbaseUrl = "http://www.12kanshu.com/plus/search.php?kwtype=0&searchtype=&q=";
    private String lwbaseUrl = "https://www.lewen8.com/searchbook.php?keyword=";

    public String search_xbq(String title){
        String fullURL = baseUrl+title;
        HttpResponse response = joddHttp.sendBrowser(fullURL, fullURL);
        if(response.statusCode()== HttpURLConnection.HTTP_OK){
            String body = response.body();
            Document doc = Jsoup.parse(body);
            //body > div.result-list > div > div.result-game-item-detail > h3 > a
            Elements items = doc.select("a.result-game-item-title-link");
            if(items!=null && items.size()>0){
                final Element element = items.get(0);
                String href = element.attr("href");
                return href;
            }
        }else{
            logger.warn("search_xbq failed! get ["+response.body()+"]");
        }
        return null;
    }

    public String search_yq(String title){
        String fullURL = yqbaseUrl+title;
        HttpResponse response = joddHttp.sendBrowser(fullURL, fullURL);
        if(response.statusCode()== HttpURLConnection.HTTP_OK){
            String body = response.body();
            Document doc = Jsoup.parse(body);
            //body > div.result-list > div > div.result-game-item-detail > h3 > a
            Elements items = doc.select("h4.bookname a");
            if(items!=null && items.size()>0){
                final Element element = items.get(0);
                String href = element.attr("href");
                return "http://www.yqxs.cc"+href;
            }
        }else{
            logger.warn("search_xbq failed! get ["+response.body()+"]");
        }
        return null;
    }

    public String search_kk(String title){
        if(title.length()>10){
            title = title.substring(0,10);
        }
        String fullURL = null;
        try {
            fullURL = kkbaseUrl+ URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e);
        }
        HttpResponse response = joddHttp.sendBrowser(fullURL, "http://www.12kanshu.com");
        //System.out.println(fullURL);
        if(response.statusCode()== HttpURLConnection.HTTP_OK){
            String body = response.body();
            Document doc = Jsoup.parse(body);
            //body > div:nth-child(4) > div:nth-child(2) > div.search_res.box > ul > li > h2 > a
            Elements items = doc.select("div.search_res.box ul li h2 a");
            if(items!=null && items.size()>0){
                final Element element = items.get(0);
                String href = element.attr("href");
                if(!href.equals("#/")){
                    return "http://www.12kanshu.com"+href;
                }
            }
        }else{
            logger.warn("search_xbq failed! get ["+response.body()+"]");
        }
        return null;
    }

    public String search_lw(String title){
        final String maohao = "：";
        if(title.contains(maohao)){
            int idx = title.indexOf(maohao);
            title = title.substring(idx+1);
        }
        String fullURL = null;
        try {
            fullURL = lwbaseUrl+ URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e);
        }
        HttpResponse response = joddHttp.sendBrowser(fullURL, "https://www.lewen8.com");
        //System.out.println(fullURL);
        if(response.statusCode()== HttpURLConnection.HTTP_OK){
            String body = response.body();
            Document doc = Jsoup.parse(body);
            //#alistbox > div.info > div.yuedu > a
            Elements items = doc.select("div.yuedu a");
            if(items!=null && items.size()>0){
                final Element element = items.get(0);
                String href = element.attr("href");
                if(!href.equals("#/")){
                    return "https://www.lewen8.com"+href;
                }
            }
        }else{
            logger.warn("search_xbq failed! get ["+response.body()+"]");
        }
        return null;
    }

    /**
     *
     * @param title
     * @return
     */
    @Deprecated
    public String search_wq(String title){
        String fullURL = wqbaseUrl+title;
        logger.info("fullURL:"+fullURL);
        try{
            HttpResponse response = joddHttp.send(fullURL, fullURL);
            if(response.statusCode()== HttpURLConnection.HTTP_MOVED_TEMP){
                return "http://www.wuqingshui.com"+response.header("Location");
            }
        }catch (Exception e){
           logger.warn(e.getMessage());
        }
        return null;
    }

    public String search(String title) {
        String url = this.search_xbq(title);
        if(url==null){
            url = this.search_yq(title);
        }
        if(url==null){
            url = this.search_kk(title);
        }
        if(url==null){
            url = this.search_lw(title);
        }
        return url;
    }

    public List<String> searchAll(String title) {
        List<String> rets = new ArrayList<>();
        String url = this.search_xbq(title);
        if(url!=null){
            rets.add(url);
        }
        url = this.search_yq(title);
        if(url!=null){
            rets.add(url);
        }
        url = this.search_kk(title);
        if(url!=null){
            rets.add(url);
        }
        url = this.search_lw(title);
        if(url!=null){
            rets.add(url);
        }

        return rets;
    }

    public static void main(String[] args) {
        SearchService biquGSearchService = new SearchService();
        biquGSearchService.joddHttp = new JoddHttp();
        String title ="烽烟乱世遇佳人";
        System.out.println(biquGSearchService.search_lw(title));
    }
}
