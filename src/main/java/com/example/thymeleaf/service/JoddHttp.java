package com.example.thymeleaf.service;

import com.example.thymeleaf.util.AppUtils;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by lixiaoming on 2018/6/28.
 */
@Service
public class JoddHttp {

    private static Logger logger = LoggerFactory.getLogger(JoddHttp.class);

    final  static String[] USER_AGENTS = {
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
                "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
                "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
                "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
                "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
                "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
                "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
                "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
                "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36"
    };

    private Map<String,HttpBrowser> browsers = new ConcurrentHashMap<>();

    Map<Thread,String> threadLocal = new HashMap<>();

    public HttpResponse send(String url,String refer){
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.header("User-Agent",randomUA());
        httpRequest.header("referer",refer);
        httpRequest.followRedirects(false);
        //httpRequest.header("cookie","Hm_lvt_5eb81c3b57ea700d51556a83f9cebcfe=1529906198; PHPSESSID=vafq33vir6fs9h49ka803j6eh6; fikker-QxUb-iPW6=ihM43lWfiX7VCM70LDadQVyTSdePAFQI; fikker-QxUb-iPW6=ihM43lWfiX7VCM70LDadQVyTSdePAFQI; fikker-G63t-NHOI=SJAbMdehiJUDWfKFJ8sRwBkpc4QR5d3b; fikker-G63t-NHOI=SJAbMdehiJUDWfKFJ8sRwBkpc4QR5d3b; fikker-EvqM-cpAV=cB3KyFSvmrcdSRIR7HwIKTNRU63lC0cP; fikker-EvqM-cpAV=cB3KyFSvmrcdSRIR7HwIKTNRU63lC0cP; fikker-cdgH-UTIt=iRQ6QXipabJJUH7OsT1dwTQjqrirW6OK; Hm_lpvt_5eb81c3b57ea700d51556a83f9cebcfe=1530238589");
        HttpResponse response = httpRequest.send();
        return response;
    }

    public void removeDomain(Thread thread){
        String domainName = threadLocal.get(thread);
        if(domainName!=null){
            browsers.remove(domainName);
        }
    }

    public HttpResponse sendBrowser(String url,String refer) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<HttpResponse> futureTask = new FutureTask<>(() -> sendBrowserInner(url,refer));
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            return futureTask.get(3, TimeUnit.MINUTES);
        }finally {
            if(thread.isAlive()){
                //logger.info("clear thread!");
                thread.interrupt();
            }
        }
    }

    public HttpResponse sendBrowserInner(String url,String refer) {
        String domainName = null;
        try {
            domainName = AppUtils.extraDomain(url);
        } catch (MalformedURLException e) {
            logger.warn("",e);
            return null;
        }
        threadLocal.put(Thread.currentThread(),domainName);
        HttpBrowser browser = browsers.get(domainName);

        if(browser==null){
            browser = new HttpBrowser();
            //信任所有证书
            //browser.getHttpRequest().trustAllCerts(true);
            browsers.put(domainName,browser);
        }
        HttpRequest request = HttpRequest.get(url);
        request.header("User-Agent",randomUA());
        request.header("referer",refer);
        request.trustAllCerts(true);
        try{
            browser.sendRequest(request);
        }catch (Exception e){
            browsers.remove(domainName);
            logger.warn("sendRequest failed,remove "+domainName+" from browsers",e);
        }

        HttpResponse response = browser.getHttpResponse();
        return response;
    }

    public HttpResponse sendBrowserByGet(String url,String refer,Map formMap) {
        String domainName = null;
        try {
            domainName = AppUtils.extraDomain(url);
        } catch (MalformedURLException e) {
            logger.warn("",e);
            return null;
        }
        HttpBrowser browser = browsers.get(domainName);
        if(browser==null){
            browser = new HttpBrowser();
            browsers.put(domainName,browser);
        }
        HttpRequest request = HttpRequest.get(url);
        String mac = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        //request.header("User-Agent",mac);
        request.header("referer",refer);
        request.form(formMap);
        browser.sendRequest(request);
        HttpResponse response = browser.getHttpResponse();
        return response;
    }

    public HttpResponse sendBrowserByPost(String url,String refer,Map formMap) {
        String domainName = null;
        try {
            domainName = AppUtils.extraDomain(url);
        } catch (MalformedURLException e) {
            logger.warn("",e);
            return null;
        }
        HttpBrowser browser = browsers.get(domainName);
        if(browser==null){
            browser = new HttpBrowser();
            browsers.put(domainName,browser);
        }
        HttpRequest request = HttpRequest.post(url);
        request.header("User-Agent",randomUA());
        request.header("referer",refer);
        request.form(formMap);
        browser.sendRequest(request);
        HttpResponse response = browser.getHttpResponse();
        return response;
    }

    private String randomUA() {
        int length = USER_AGENTS.length;
        Random random = new Random();
        int idx = random.nextInt(length);
        return USER_AGENTS[idx];
    }

    private void test1() throws InterruptedException, ExecutionException, TimeoutException {
        String url = "https://m.xiaoshuoli.com/ph/week_1.html";
        HttpResponse response = sendBrowser(url, url);
        System.out.println(response);
    }

    private void test2(){
        String url = "http://020mk.cn/tools/mypan.php";
        Map queryMap = new HashMap();
        queryMap.put("action","get");
        queryMap.put("token","ZMfW9rcb1NAag3l2");
        queryMap.put("key","t5");
        queryMap.put("content","this is test!!");
        HttpResponse response = sendBrowserByPost(url, url,queryMap);
        System.out.println("["+response.body()+"]");
    }

    private void test3()  {
        String url = "https://www.aikantxt.la/content.php";
        //nbid=610&crid=11622741&fid=fb96549631c835eb239cd614cc6b5cb7d295121a
        Map queryMap = new HashMap();
        queryMap.put("nbid","610");
        queryMap.put("crid","11622741");
        queryMap.put("fid","fb96549631c835eb239cd614cc6b5cb7d295121a");
        HttpResponse response = sendBrowserByPost(url, url,queryMap);
        byte[] resp = response.bodyBytes();
        try {
            System.out.println("["+new String(resp,"GBK")+"]");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws MalformedURLException {
        JoddHttp joddHttp = new JoddHttp();
        joddHttp.test3();

    }
}
