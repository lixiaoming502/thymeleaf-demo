package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
import jodd.http.HttpResponse;
import jodd.util.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@RestController
public class CrawlerController {
    private static Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    @Autowired
    private JoddHttp joddHttp;

    @Autowired
    private SearchService biquGSearchService;

    @Autowired
    private WooniuSynIdService wooniuSynIdService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @RequestMapping("/contents/myproject1/test1.php")
    public void proxy(@RequestParam("url") String base64URL, HttpServletResponse httpServletResponse){
        String url = new String(Base64.decode(base64URL));
        logger.info("recev:["+url+"]");
        HttpResponse response = null;
        try {
            response = joddHttp.sendBrowser(url, url);
            logger.info("sendBrowser complete ");
            byte[] resp = response.bodyBytes();
            httpServletResponse.getOutputStream().write(resp);
            httpServletResponse.getOutputStream().flush();
        } catch (Exception e) {
            logger.warn("proxy failed!");
        }
    }

    @RequestMapping("/contents/myproject1/search.php")
    public String search(String title) throws InterruptedException, ExecutionException, TimeoutException {
        logger.info("get title "+title);
        return biquGSearchService.search_xbq(title.trim());
    }

    @RequestMapping("/contents/myproject1/statistics.php")
    public int statistics(String date) throws InterruptedException, ExecutionException, TimeoutException {
        logger.info("get date "+date);
        return wooniuSynIdService.querySynCountByDate(date.trim());
    }

    @RequestMapping("/contents/myproject1/submitUrl.php")
    public int submitUrl(String futureURL,int seedId) throws InterruptedException, ExecutionException, TimeoutException, MalformedURLException {
        futureURL = new String(Base64.decode(futureURL));
        logger.info("get futureURL "+futureURL);

        String domainName = AppUtils.extraDomain(futureURL);
        FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomain(domainName);
        Integer domainId = cfg.getId();

        FutureCrawler futureCrawler  = new FutureCrawler();
        futureCrawler.setPageUrl(futureURL);
        futureCrawler.setCrawlerState("A");
        futureCrawler.setDomainId(domainId);
        futureCrawler.setCreateTime(new Date());
        futureCrawler.setSeedId(seedId);
        futureCrawler.setLevel(1);
        futureCrawler.setCallbackStatus(1);
        futureCrawlerService.addNew(futureCrawler);

        return futureCrawler.getId();
    }

    @RequestMapping("/contents/crawler/resp.php")
    public String getSeedIdResponse(int crawlerId) throws IOException {
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        if(record==null){
            return "Not Exist";
        }
        if(record.getCrawlerState().equals("F")){
            String path = record.getResponse();
            String content = FileUtils.readFileToString(new File(path), "UTF8");
            return content;
        }else if(record.getCrawlerState().equals("E")){
            return "Error";
        }
        return "Processing";
    }

    @RequestMapping("/contents/crawler/clear.php")
    public String clearCrawler(int crawlerId) throws IOException {
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        if(record.getCrawlerState().equals("F")){
            String path = record.getResponse();
            deleteFile(path);
            clearPageCrawler(record);
        }
        return "SUCCESS";
    }

    private void deleteFile(String localUrl) {
        File file = new File(localUrl);
        if(file.exists()){
            logger.info("delete file "+localUrl);
            file.delete();
        }
    }

    private void clearPageCrawler(FutureCrawler record) {
        if(record!=null){
            deleteFile(record.getResponse());
            List<FuturePageLoader> lst = futurePageLoaderService.getByCrawlerId(record.getId());
            for(FuturePageLoader futurePageLoader:lst){
                deleteFile(futurePageLoader.getResponse());
            }
        }
    }
}
