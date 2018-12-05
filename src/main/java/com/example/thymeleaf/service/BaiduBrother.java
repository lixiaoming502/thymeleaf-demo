package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.util.AppUtils;
import com.example.thymeleaf.vo.SearchResultPage;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.net.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lixiaoming on 2018/8/1.
 */
@Component
public class BaiduBrother {

    private static Logger logger = LoggerFactory.getLogger(BaiduBrother.class);

    private String searchBaseURL = "https://www.baidu.com/s?ie=utf-8&wd=";
    private String baseURL = "https://www.baidu.com";
    //搜索失败后最大尝试次数
    private static final int MAX_SEARCH_COUNT = 3;
    //要比较的文本的最小长度
    private final static int TXT_MIN_LENGTH = 5;
    //最小的文本相似度
    private final static double TXT_SIMILAR_MIN_VALUE = 0.7d;
    //最大候选数目
    private final static int MAX_CANDITATE_VALUE = 10;

    //private final HashSet<String> locations = new HashSet<>();

    @Autowired
    private JoddHttp joddHttp;

    @Autowired
    private ChapterService chapterService;

    public List<String> loadPureChpLst(int articleId){
        List<String> ret = new ArrayList<>();
        List<Chapter> lst = chapterService.selectByArticleId(articleId);
        lst.stream().forEach(chapter -> {
            String title = chapter.getTitle();
            String pureTitle = AppUtils.extractChapTitle(title);
            ret.add(pureTitle);
        });
        return ret;
    }

    public SearchResultPage search(String title) throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
        //避免重复analysis
        //locations.clear();
        SearchResultPage searchResultPage = searchInner(title);
        int count = 0;
        while (searchResultPage.getResultItems().size()==0&&count< MAX_SEARCH_COUNT){
            logger.info("try search again,count:"+count);
            count++;
            searchResultPage = searchInner(title);
        }
        return searchResultPage;
    }

    /**
     *
     * @param title
     * @return 找出一页搜索结果，或者是最多尝试3次
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public SearchResultPage searchInner(String title) throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
        String fullURL = searchBaseURL+ URLEncoder.encode(title,"utf8");
        HttpResponse response = joddHttp.sendBrowser(fullURL, baseURL);
        final int statusCode = response.statusCode();
        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.setStatusCode(statusCode);
        if(statusCode == HttpStatus.ok().status()){
            String body = new String(response.bodyBytes(),"utf8");
            Document doc = Jsoup.parse(body);
            Elements items = doc.select("h3.t a");
            Elements nexts = doc.select("a.n");
            for(Element item:items) {
                String itemHref = item.attr("href");
                searchResultPage.addItem(itemHref);
            }
            if(items!=null&&items.size()>0){
                Element next = nexts.first();
                String nextHref = baseURL+next.attr("href");
                searchResultPage.setNextPage(nextHref);
            }
        }else{
           logger.info("get response code ["+ statusCode +"]");
        }

        return searchResultPage;
    }

    public SearchResultPage searchNextFromURL(String url) throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
        HttpResponse response = joddHttp.sendBrowser(url, baseURL);
        final int statusCode = response.statusCode();
        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.setStatusCode(statusCode);
        if(statusCode == HttpStatus.ok().status()){
            String body = new String(response.bodyBytes(),"utf8");
            Document doc = Jsoup.parse(body);
            Elements items = doc.select("h3.t a");
            Elements nexts = doc.select("a.n");
            for(Element item:items) {
                String itemHref = item.attr("href");
                searchResultPage.addItem(itemHref);
            }
            if(items!=null&&items.size()>0){
                Element next = nexts.first();
                String nextHref = baseURL+next.attr("href");
                searchResultPage.setNextPage(nextHref);
            }
        }else{
            logger.info("get response code ["+ statusCode +"]");
        }

        return searchResultPage;
    }

    private String metaCharset(Document doc) {
        Elements items = doc.select("meta");
        String ret = null;
        for(Element item:items){
            String sel1 = item.attr("charset");
            String sel2 = item.attr("content");
            if(StringUtils.isNotEmpty(sel1)){
                ret =  sel1;
                break;
            }else if(StringUtils.isNotEmpty(sel2)&&sel2.toLowerCase().contains("charset")){
                int index1 = sel2.indexOf("=");
                ret =  sel2.substring(index1+1);
                break;
            }
        }
        //UTF-8,转换为utf8
        ret = ret.toLowerCase();
        if(ret.contains("utf")){
            ret = "utf8";
        }
        return ret;
    }

    public Pair analysisCssSelector(String itemUrl, List<String> pureChpLst,HashSet<String> locations) throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
        HttpResponse response = joddHttp.sendBrowser(itemUrl, baseURL);
        HttpRequest request = response.getHttpRequest();
        String location = request.protocol()+"://"+request.host()+request.path();
        logger.info("location:"+location);
        if(locations.contains(location)){
            logger.info("exsit location!");
            return null;
        }else{
            locations.add(location);
        }
        final int statusCode = response.statusCode();
        if(statusCode == HttpStatus.ok().status()){
            String body = new String(response.bodyBytes(),"utf8");
            Document doc = Jsoup.parse(body);
            String charset = metaCharset(doc);
            logger.info("metaCharset:"+charset);
            if(!charset.equals("utf8")){
                body = new String(response.bodyBytes(),charset);
                doc = Jsoup.parse(body);
            }
            Elements tagAlst = doc.select("a");
            HashMap<Element,Double> scoreMap = new HashMap<>();
            for(Element tagAitem:tagAlst){
                String text = tagAitem.text().trim();
                //tagAitemMap.put(text,tagAitem);
                String pureText = AppUtils.extractChapTitle(text);
                if(pureText.length()> TXT_MIN_LENGTH){
                    //计算最大相似度
                    double similar = jaccardSimilarity(pureText,pureChpLst);
                    if(similar>TXT_SIMILAR_MIN_VALUE){
                        scoreMap.put(tagAitem,similar);
                        if(scoreMap.size()>MAX_CANDITATE_VALUE){
                            break;
                        }
                    }
                }
            }
            List<Element> sels = new ArrayList<>();
            scoreMap.forEach((k,v)->{
                //logger.info(k.cssSelector());
                sels.add(k);
            });
            String cssSelector = commonAncestor(sels,sels.size());
            logger.info("commonAncestor:"+cssSelector);
            if(cssSelector!=null){
                return new ImmutablePair(location,cssSelector+"|"+charset);
            }
        }else{
            logger.info("get response code ["+ statusCode +"]");
        }
        return null;
    }

    private String commonAncestor(List<Element> sels,int maxFit) {
        final String[] selector = {null};
        sels.stream().forEach(element -> {
           selector[0] = element.cssSelector();
           logger.info("selector[0]:"+selector[0]);
        });
        String ret = removeLastNthChild(selector[0]);
        return ret;
    }

    private String commonAncestor1(List<Element> sels,int maxFit) {
        Map<String,Integer> countMap = new HashMap<>();
        List<Element> parentLst = new ArrayList<>();
        final int[] maxCount = {0};
        sels.stream().forEach(element -> {
            Element parent = element.parent();
            parentLst.add(parent);
            String parentSelector = parent.cssSelector();
            int count;
            if (countMap.containsKey(parentSelector)) {
                count = countMap.get(parentSelector);
                count = count + 1;
            } else {
                count = 1;
            }
            if(maxCount[0] <count){
                maxCount[0] = count;
            }
            countMap.put(parentSelector, count);
        });
        if(maxCount[0]<maxFit){
            return commonAncestor(parentLst,maxFit);
        }else{
            for(String selector:countMap.keySet()){
                if(countMap.get(selector)>=maxCount[0]){
                    return selector;
                }
            }
        }
        return null;
    }

    private double jaccardSimilarity(String pureText,List<String> pureChpLst) {
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        double similar = 0;
        for(String s:pureChpLst){
            Double e = jaccardSimilarity.apply(s, pureText);
            if(e==1.0){
                return e;
            }else if(e>similar){
                similar = e;
            }
        }
        return similar;
    }

    private String removeLastNthChild(String org){
        //String org = "html > body > section.w-all > div.card.mt20:nth-child(4) > div.body > ul.dirlist.clearfix > li:nth-child(15) > a";
        String child = "nth-child";
        int idx1 = org.lastIndexOf(child);
        int idx2 = org.indexOf(">",idx1);
        String pre = org.substring(0,idx1-1);
        String end = org.substring(idx2-1);
        String ret = pre + end;
        logger.info(org + "转换为：" + end);
        return ret;
    }

}
