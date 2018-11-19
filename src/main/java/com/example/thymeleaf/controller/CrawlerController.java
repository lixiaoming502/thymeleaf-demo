package com.example.thymeleaf.controller;

import com.example.thymeleaf.service.JoddHttp;
import com.example.thymeleaf.service.SearchService;
import jodd.http.HttpResponse;
import jodd.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

}
