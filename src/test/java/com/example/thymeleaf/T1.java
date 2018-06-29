package com.example.thymeleaf;

import com.example.thymeleaf.service.JoddHttp;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.util.Base64;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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

    private void testDecodeBase64() throws UnsupportedEncodingException {
        String base64URL = "aHR0cDovL3d3dy44MXp3LmNvbS9ib29rLzI0NDgyLw==";
        String url = new String(Base64.decode(base64URL));
        System.out.println(url);
        JoddHttp joddHttp = new JoddHttp();
        HttpResponse response = joddHttp.sendBrowser(url, url);
        Map<String, String[]> headers = response.headers();
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

    public static void main(String[] args) throws IOException {
        T1 t1 = new T1();
        //t1.testBrowser();
        //t1.testDecodeBase64();
        t1.genSql();

    }
}
