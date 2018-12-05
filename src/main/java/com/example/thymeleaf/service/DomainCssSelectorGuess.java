package com.example.thymeleaf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.DomainCssSelector;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import jodd.http.HttpResponse;
import jodd.http.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixiaoming on 2018/12/3.
 */
@Component
public class DomainCssSelectorGuess extends AbstractCallBack {

    public static final int L2_PARSE_TO_BE_CHECK = 0;
    private static Log logger = LogFactory.getLog(DomainCssSelectorGuess.class);


    public Map<Integer, Integer> guessMap = new ConcurrentHashMap<>();

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Autowired
    private DomainCssSelectorService domainCssSelectorService;

    @Autowired
    private JoddHttp joddHttp;

    public String removeGuessMap(int domainId) {
        guessMap.remove(domainId);
        return "OK";
    }

    public String guess2(int domainId, int pageLevel) throws Exception {
        String ret = "IN-PROCESSING";
        FutureCrawlerCfg cfg = futureCrawlerCfgService.queryByDomainId(domainId);
        DomainCssSelector cssSelector = domainCssSelectorService.queryByDomainId(domainId);
        String rules = cssSelector.getExtraRule();
        JSONObject jsonRule = JSONObject.parseObject(rules);
        String l2ExampleUrl = jsonRule.getString("l2_example_url");
        if (cfg.getEnable().equals("A")) {
            int crawlerId = -999;
            if(guessMap.containsKey(domainId)){
                crawlerId = guessMap.get(domainId);
            }
            String l3ExampleUrl = null;
            switch (pageLevel) {
                case 2:
                    if (!guessMap.containsKey(domainId)) {
                        //第一次尝试抓取
                        String crawlerState = "A";
                        int level = 2;
                        addFutureCrawler(domainId, cfg, l2ExampleUrl, crawlerState, level);
                        guessMap.put(domainId, L2_PARSE_TO_BE_CHECK);
                        ret = "L2_PARSE_READY";
                    } else if (crawlerId > L2_PARSE_TO_BE_CHECK) {
                        //第二次尝试抓取
                        //已经爬取完毕，则应该做处理，默认为2级页面
                        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
                        JSONArray jsonArray = parseToJsonArray(record);
                        //JSONArray排序
                        List<JSONObject> lst = parseToList(jsonArray);
                        logger.info("list size " + lst.size());

                        for (JSONObject info : lst) {
                            //int seqId = info.getInteger("seqId");
                            String title = info.getString("chapTitle");
                            String url = info.getString("href");
                            info.put("size",lst.size());
                            if (StringUtils.isEmpty(url) || StringUtils.isEmpty(title)) {
                                return "url is empty,level2 parse failed!";
                            }
                            l3ExampleUrl = url;
                            jsonRule.put("l3_example_url", l3ExampleUrl);
                            cssSelector.setExtraRule(jsonRule.toJSONString());
                            domainCssSelectorService.update(cssSelector);
                            //guessMap.put(domainId, L2_PARSE_OK);
                            removeGuessMap(domainId);
                            return info.toJSONString();
                        }
                    }else {
                        ret = "L2_PARSE_TO_BE_CHECK";
                    }
                    break;
                case 3:
                    l3ExampleUrl = jsonRule.getString("l3_example_url");
                    logger.info("l3ExampleUrl "+l3ExampleUrl);
                    HttpResponse response = joddHttp.sendBrowser(l3ExampleUrl, l3ExampleUrl);
                    if(response.statusCode()== HttpStatus.HTTP_OK){
                        byte[] bytes = response.bodyBytes();
                        String content = new String(bytes,cfg.getCharset());
                        String l3CssSelector = guessL3CssSelector(content);
                        File temp = File.createTempFile("temp-file-name", ".tmp");
                        FileUtils.writeStringToFile(temp,content,"utf8");
                        return l3CssSelector+"|"+temp.getAbsolutePath()+"\n" ;
                    }else{
                        return "statusCode:"+response.statusCode();
                    }
            }
        } else {
            ret = "crawler cfg enable not equals to A";
        }
        return ret;
    }

    private String guessL3CssSelector(String content) {
        Document doc = Jsoup.parse(content);
        Elements items = doc.getElementsByTag("div");
        String ret = null;
        StringBuilder strBuilder = new StringBuilder();
        for (Element item : items) {
            String id = item.id();
            if (StringUtils.isNotEmpty(id)) {
                int len = item.text().length();
                logger.info("div id:" + id+" len:"+len);
                strBuilder.append("div id:" + id+" len:"+len);
                if (id.toLowerCase().contains("content")) {
                    ret = id;
                }
            }
        }
        if(StringUtils.isEmpty(ret)){
            ret = strBuilder.toString();
        }
        return ret;
    }

    private void addFutureCrawler(int domainId, FutureCrawlerCfg cfg, String l2ExampleUrl, String crawlerState, int level) {
        FutureCrawler futureCrawler = new FutureCrawler();
        futureCrawler.setCrawlerState(crawlerState);
        futureCrawler.setDomainId(domainId);
        futureCrawler.setPageUrl(l2ExampleUrl);
        futureCrawler.setCreateTime(new Date());
        futureCrawler.setSeedId(cfg.getDefaultParserSeed());
        futureCrawler.setLevel(level);
        futureCrawler.setCallbackStatus(0);
        futureCrawler.setCallbackBean("domainCssSelectorGuess");
        futureCrawler.setCallbackLevel(level);
        futureCrawlerService.addNew(futureCrawler);
    }

    @Override
    protected void callback_level3(int crawlerId) {
        logger.info("callback_level3 entry!crawlerId " + crawlerId);
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        guessMap.put(record.getDomainId(), crawlerId);
    }

    @Override
    protected void callback_level2(int crawlerId) {
        logger.info("callback_level2 entry!crawlerId " + crawlerId);
        FutureCrawler record = futureCrawlerService.queryByCrawlerId(crawlerId);
        guessMap.put(record.getDomainId(), crawlerId);
    }

    @Override
    protected void callback_level1(int crawlerId) {

    }
}
