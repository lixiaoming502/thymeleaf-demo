package com.example.thymeleaf.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.selfdrive.DriverFuture;
import com.example.thymeleaf.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class XslCrawler extends AbstractCrawler{

    @Autowired
    private ArticleService articleService;

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    private String baseUrl = "https://m.xiaoshuoli.com";

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

    @Override
    protected boolean parseLevel4(int crawlerId, String url) {
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.isDone()) {
            String respone = (String) future.getRespone();
            Document doc = Jsoup.parse(respone);
            Elements review = doc.select("p.review");
            Elements title = doc.select("h1.title");
            Elements items = doc.select(".btn a");
            String infoUrl = url;
            Elements sort = doc.select("p.sort");
            Elements img = doc.select("div img");
            Elements author = doc.select("p.author");
            //status,img,author
            Article article = new Article();
            String commend = review.get(0).text();
            article.setComment(commend);
            String titleTxt = title.get(0).text();
            article.setTitle(titleTxt);
            String listUrl = items.get(0).attr("href");
            article.setListUrl(listUrl);
            article.setInfoUrl(infoUrl);
            int sortV = convertSort(sort.get(0).text());
            article.setCategoryId(sortV);
            article.setImg(img.get(0).attr("href"));
            article.setAuthor(author.get(0).text());
            Article exist = articleService.queryByListURL(listUrl);
            if(exist==null){
                articleService.addRecord(article);
            }else{
                article.setId(exist.getId());
                articleService.update(article);
            }
            try {
                futureCrawlerService.finish(crawlerId,"F",JSONObject.toJSONString(article));
            } catch (IOException e) {
                logger.warn("",e);
            }
            return true;
        }
        return false;
    }

    @Override
    protected String sensitiveWordsFilter(String htmlContent) {
        return htmlContent;
    }

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        final String cssQueryNext = "#pb_next";
        final String cssQueryContent = "#chaptercontent";
        //判断是否所有子任务都是F
        if(hasCrawlerPageWaiting(crawlerId)){
            return false;
        }
        DriverFuture future = createDriverFuture(crawlerId, url);
        if(future.getStatusCode()!=200){
            logger.info("getStatusCode!=200,statusCode "+future.getStatusCode());
            //初始状态，什么原因？判断为：子任务列表为空的情况,则需要等待子任务执行完
            if(future.getStatusCode()==0){
                return false;
            }else{
                return updateCrawlerResponse(crawlerId, "E", "");
            }
        }
        if(future.isDone()) {
            String respone = (String) future.getRespone();
            Document doc = Jsoup.parse(respone);

            Elements items = doc.select(cssQueryNext);
            if(items.size()>0){
                Element next = items.get(0);
                String nextUrl = next.attr("href");
                logger.info("nextUrl:"+nextUrl);
                DriverFuture futureNext = createDriverFuture(crawlerId, baseUrl+nextUrl);
                if(futureNext.isDone()){
                    String respone2 = (String) futureNext.getRespone();
                    String c1 = extratPageConent(cssQueryContent, respone);
                    String c2 = extratPageConent(cssQueryContent, respone2);
                    String s1 = parseString(c1);
                    String s2 = parseString(c2);
                    return updateCrawlerResponse(crawlerId, "F", s1+s2);
                }
            }else{
                logger.info("next page is not found!");
                return updateCrawlerResponse(crawlerId, "E", "");
            }
        }
        return false;
    }

    private String parseString(String input){
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        StringBuffer strbuf=new StringBuffer();
        try {
            while ( (line = br.readLine()) != null ) {
                if(!line.contains("请点击下一页继续阅读")&&
                        !line.contains("addBookMarkByManual")){
                    strbuf.append(line+"\n");
                }
            }
        } catch (IOException e) {
            logger.warn("",e);
        }

        return strbuf.toString();
    }

    private String extratPageConent(String cssQueryContent, String respone2) {
        Document doc2 = Jsoup.parse(respone2);
        Elements items2 = doc2.select(cssQueryContent);
        String content2 = "";
        if(items2!=null&&items2.size()>0){
            content2 = items2.get(0).html();
        }
        return content2;
    }

    public boolean parseLevel2(int crawlerId, String url){
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
