package com.example.thymeleaf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.service.JoddHttp;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.util.Base64;
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

    private string correct(string title) {
        if(title.length()>3){
            title = title.substring(0,3);
        }
        return title;
    }

    private void testcorrect(){
        string title = "徐漫陆亦深小说免费阅读";
        system.out.println(correct(title));
    }

    private void testbrowser(){
        httpbrowser browser = new httpbrowser();

        httprequest request = httprequest.get("https://m.xiaoshuoli.com/ph/week_1.html");
        browser.sendrequest(request);
        string page = browser.getpage();
        httpresponse response = browser.gethttpresponse();
        int statuscode  = response.statuscode();
        system.out.println(statuscode);

    }

    private void testdecodebase64() throws unsupportedencodingexception, interruptedexception, executionexception, timeoutexception {
        string base64url = "ahr0cdovl3d3dy44mxp3lmnvbs9ib29rlzi0ndgylw==";
        string url = new string(base64.decode(base64url));
        system.out.println(url);
        joddhttp joddhttp = new joddhttp();
        httpresponse response = joddhttp.sendbrowser(url, url);
        //map<string, string[]> headers = response.headers();
        //string s2 = new string(response.bodybytes(), "gbk");
        string s1 = response.contenttype();
        system.out.println("response.charset():"+response.charset());
        string s2 = response.bodytext();
        system.out.println(s2);
    }

    private void gensql() throws ioexception {
        string url_base = "https://m.xiaoshuoli.com/ph/week_";
        string sql_base = "insert into `t_future_crawler` (`seed_id`, `level`, `page_url`, `response`, `crawler_state`, `create_time`, `update_time`) " + "values " +
                "\t(12, 1, '$url', null, 'a', null, '2018-06-29 15:58:29');\n";
        filewriter filewriter = new filewriter("data/sql.txt");
        for(int i=2;i<5101;i++){
            string nurl = url_base+i+".html";
            string sql = sql_base.replace("$url",nurl);
            //system.out.println(sql);
            filewriter.write(sql);
        }
        filewriter.close();
    }

    private void t2() throws unsupportedencodingexception {
        string title ="我的漂亮女同事（女总裁的非常保镖）";
        system.out.println(urlencoder.encode(title,"utf-8"));
        string d1 = "%e6%88%91%e7%9a%84%e6%bc%82%e4%ba%ae%e5%a5%b3%e5%90%8c%e4%ba%8b%ef%bc%88%e5%a5%b3%e6%80%bb";
        system.out.println(urldecoder.decode(d1));
    }

    private void sortjson(){
        string path = "/users/lixiaoming/downloads/crawler_10972.txt";
        jsonarray jsonarray = parsetojsonarray(path);
        list<jsonobject> lst = jsonarray.tojavalist(jsonobject.class);
        collections.sort(lst, new comparator<jsonobject>() {
            @override
            public int compare(jsonobject o1, jsonobject o2) {
                int seqid1 = o1.getinteger("seqid");
                int seqid2 = o2.getinteger("seqid");
                return seqid1-seqid2;
            }
        });
        for(jsonobject o:lst){
            system.out.println(o.getinteger("seqid"));
        }

    }

    private jsonarray parsetojsonarray(string path ) {
        jsonarray jsonarray = null;
        try {
            string content  = fileutils.readfiletostring(new file(path),"utf8");
            jsonarray = jsonarray.parsearray(content);
        } catch (ioexception e) {
            e.printstacktrace();
        }
        return jsonarray;
    }

    private void ismatch(){
        string content = "/html/102/102846/24330165.html";
        pattern pattern = pattern.compile("/html/\\d+/\\d+/\\d+.html");
        matcher matcher = pattern.matcher(content);
        system.out.println(matcher.find());
    }

    private void test() throws ioexception {
        string url = "/users/lixiaoming/downloads/874941.txt";
        string respone = fileutils.readfiletostring(new file(url),"utf8");
        document doc = jsoup.parse(respone);
        final string cssquerynext = "#pb_next";
        final string cssquerycontent = "#chaptercontent";

        elements items = doc.select(cssquerynext);
        if(items.size()>0) {
            element next = items.get(0);
            string nexturl = next.attr("href");
            system.out.println("nexturl:" + nexturl);
            string c1 = extratpageconent(cssquerycontent, respone);
            system.out.println(c1);
        }
    }

    private string extratpageconent(string cssquerycontent, string respone2) {
        document doc2 = jsoup.parse(respone2);
        elements items2 = doc2.select(cssquerycontent);
        string content2 = "";
        if(items2!=null&&items2.size()>0){
            content2 = items2.get(0).html();
        }
        return content2;
    }




    public static void main(string[] args) throws ioexception {
        t1 t1 = new t1();
        //t1.testbrowser();
        //t1.testdecodebase64();
        //t1.gensql();
        //t1.sortjson();
        t1.test();


    }
}
