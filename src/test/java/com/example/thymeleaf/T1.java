package com.example.thymeleaf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.service.JoddHttp;
import com.google.common.cache.CacheBuilder;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixiaoming on 2018/6/28.
 */
public class T1 {

    private String correct(String title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    private void testCorrect(){
        String title = "徐漫陆亦深小说免费阅读";
        System.out.println(correct(title));
    }

    private void testBrowser(){
        HttpBrowser browser = new HttpBrowser();

        HttpRequest request = HttpRequest.get("https://m.xiaoshuoli.com/ph/week_1.html");
        browser.sendRequest(request);
        String page = browser.getPage();
        HttpResponse response = browser.getHttpResponse();
        int statusCode  = response.statusCode();
        System.out.println(statusCode);

    }

    private void testDecodeBase64() throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
        String base64URL = "aHR0cDovL3d3dy44MXp3LmNvbS9ib29rLzI0NDgyLw==";
        String url = new String(Base64.decode(base64URL));
        System.out.println(url);
        JoddHttp joddHttp = new JoddHttp();
        HttpResponse response = joddHttp.sendBrowser(url, url);
        //Map<String, String[]> headers = response.headers();
        //String s2 = new String(response.bodyBytes(), "gbk");
        String s1 = response.contentType();
        System.out.println("response.charset():"+response.charset());
        String s2 = response.bodyText();
        System.out.println(s2);
    }

    private void genSql() throws IOException {
        String url_base = "https://m.xiaoshuoli.com/ph/week_";
        String sql_base = "INSERT INTO `t_future_crawler` (`seed_id`, `level`, `page_url`, `response`, `crawler_state`, `create_time`, `update_time`) " + "VALUES " +
                "\t(12, 1, '$URL', NULL, 'A', NULL, '2018-06-29 15:58:29');\n";
        FileWriter fileWriter = new FileWriter("data/sql.txt");
        for(int i=2;i<5101;i++){
            String nUrl = url_base+i+".html";
            String sql = sql_base.replace("$URL",nUrl);
            //System.out.println(sql);
            fileWriter.write(sql);
        }
        fileWriter.close();
    }

    private void t2() throws UnsupportedEncodingException {
        String title ="我的漂亮女同事（女总裁的非常保镖）";
        System.out.println(URLEncoder.encode(title,"UTF-8"));
        String d1 = "%E6%88%91%E7%9A%84%E6%BC%82%E4%BA%AE%E5%A5%B3%E5%90%8C%E4%BA%8B%EF%BC%88%E5%A5%B3%E6%80%BB";
        System.out.println(URLDecoder.decode(d1));
    }

    private void sortJson(){
        String path = "/Users/lixiaoming/Downloads/crawler_10972.txt";
        JSONArray jsonArray = parseToJsonArray(path);
        List<JSONObject> lst = jsonArray.toJavaList(JSONObject.class);
        Collections.sort(lst, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                int seqId1 = o1.getInteger("seqId");
                int seqId2 = o2.getInteger("seqId");
                return seqId1-seqId2;
            }
        });
        for(JSONObject o:lst){
            System.out.println(o.getInteger("seqId"));
        }

    }

    private JSONArray parseToJsonArray(String path ) {
        JSONArray jsonArray = null;
        try {
            String content  = FileUtils.readFileToString(new File(path),"utf8");
            jsonArray = JSONArray.parseArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void isMatch(){
        String content = "/html/102/102846/24330165.html";
        Pattern pattern = Pattern.compile("/html/\\d+/\\d+/\\d+.html");
        Matcher matcher = pattern.matcher(content);
        System.out.println(matcher.find());
    }


    private void encodeUrl() throws UnsupportedEncodingException {
        String url ="http://www.yqxs.cc/html/110/110340/21594965.html";
        String baseURL ="http://39.107.103.131:8088/contents/myproject1/test1.php?url=";
        String targetURL = baseURL+Base64.encodeToString(url);

        HttpBrowser browser = new HttpBrowser();

        HttpRequest request = HttpRequest.get(targetURL);
        browser.sendRequest(request);
        String page = browser.getPage();
        HttpResponse response = browser.getHttpResponse();


        System.out.println(new String(response.bodyBytes(),"GBK"));
    }

    private void testMd5() throws IOException {
        String path1 = "/Users/lixiaoming/tmp/11/crawler_538193.txt";
        String path2 = "/Users/lixiaoming/tmp/11/crawler_538194.txt";
        String content = FileUtils.readFileToString(new File(path1), "utf-8");
        String md5Hex = DigestUtils.md5Hex(content);

        String content2 = FileUtils.readFileToString(new File(path2), "utf-8");
        String md5Hex2 = DigestUtils.md5Hex(content2);

        System.out.println("md5Hex "+md5Hex);
        System.out.println("md5Hex2 "+md5Hex2);
    }

    private void testParse() throws IOException {
        String path ="/Users/lixiaoming/Downloads/1314397.txt";
        String cssQuery ="#chapterContent";
        String respone = FileUtils.readFileToString(new File(path), "utf-8");
        Document doc = Jsoup.parse(respone);


        Elements items = doc.select(cssQuery);
        Element content = items.get(0);
        String htmlContent = content.html();
        System.out.println(htmlContent);
    }

    private void testcacheBuilder(){
        CacheBuilder<Object, Object> cache = CacheBuilder.newBuilder();
    }

    private void json(){
        JSONObject jsonObject = new JSONObject();
        //sensitive_words
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("begin","bv");
        jsonObject.put("end","ev");
        jsonArray.add(jsonObject);
        JSONObject all = new JSONObject();
        all.put("sensitive_words",jsonArray);
        System.out.println(all.toJSONString());
    }

    private String extractPureTxt(String brotherTitle) {
        //第040章一秒不想看到
        int idx1 = brotherTitle.indexOf("第");
        int idx2 = brotherTitle.indexOf("章");
        if(idx1>-1&&idx2>idx1){
            return brotherTitle.substring(idx2+1).trim();
        }else{
            return brotherTitle;
        }
    }

    private void test_browser(){
        String url = "http://www.f96.net/3/3358/";
        HttpBrowser browser = new HttpBrowser();

        HttpRequest request = HttpRequest.get(url);
        browser.sendRequest(request);
        HttpResponse response = browser.getHttpResponse();
        int statusCode  = response.statusCode();
        System.out.println(response);
    }

    private void test_browser2() throws InterruptedException, ExecutionException, TimeoutException {
        String url = "http://www.f96.net/3/3358/";
        JoddHttp joddHttp = new JoddHttp();

        HttpResponse response = joddHttp.sendBrowser(url,url);
        int statusCode  = response.statusCode();
        System.out.println(response);
    }

    private void test_l2css(){
        String org = "html > body > section.w-all > div.card.mt20:nth-child(4) > div.body > ul.dirlist.clearfix > li:nth-child(15) > a";
        String child = "nth-child";
        int idx1 = org.lastIndexOf(child);
        int idx2 = org.indexOf(">",idx1);
        String pre = org.substring(0,idx1-1);
        String end = org.substring(idx2-1);
        System.out.println(pre+end);
        System.out.println(end);
    }


    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        T1 t1 = new T1();
        t1.test_l2css();
    }
}
