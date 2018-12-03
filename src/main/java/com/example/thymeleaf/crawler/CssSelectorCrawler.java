package com.example.thymeleaf.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.thymeleaf.model.DomainCssSelector;
import com.example.thymeleaf.service.DomainCssSelectorService;
import com.example.thymeleaf.service.MarkCleaner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixiaoming on 2018/7/14.
 */
@Component
@Scope("prototype")
public class CssSelectorCrawler extends AbstractCrawler {

    private static Logger logger = LoggerFactory.getLogger(XslCrawler.class);

    @Autowired
    private  MarkCleaner markCleaner;

    @Autowired
    private DomainCssSelectorService domainCssSelectorService;

    private DomainCssSelector domainCssSelector;

    private JSONObject jsonRules;

    CssSelectorCrawler(){
        //this.level3FilterWords = "看书小说网www.12kanshu.com";
    }

    @Override
    protected boolean parseLevel3(int crawlerId, String url) {
        DomainCssSelector cssRecord = domainCssSelectorService.queryByDomainId(domainId);
        final String cssQuery = cssRecord.getLevel3Selector();
        return parseLevel3(crawlerId, url, cssQuery);
    }

    @Override
    protected boolean parseLevel2(int crawlerId, String url) {
        DomainCssSelector cssRecord = queryDomainCssSelector();
        final String cssQuery = cssRecord.getLevel2Selector();
        String l3_preUrl = jsonRules.getString("l3_preUrl");
        return parseLevel2(crawlerId, url, cssQuery,l3_preUrl);
    }

    @Override
    protected boolean praseLevel1(int crawlerId, String url) {
        return false;
    }

    @Override
    protected String sensitiveWordsFilter(String htmlContent) {
        DomainCssSelector cssRecord = queryDomainCssSelector();
        String rules = cssRecord.getExtraRule();
        if(StringUtils.isNotEmpty(rules)){
            JSONArray sensitives = jsonRules.getJSONArray("sensitive_words");
            String newHtmlContent = htmlContent;
            for(Object sensitive:sensitives){
                JSONObject jsonSensitive = (JSONObject)sensitive;
                String begin = jsonSensitive.getString("begin");
                String end = jsonSensitive.getString("end");
                newHtmlContent = markCleaner.replaceWithStrDelimer(newHtmlContent,begin,end);
            }
            if(newHtmlContent.length()/htmlContent.length()*100<80){
                logger.info("sensitiveWordsFilter may be error");
                return newHtmlContent;
            }
        }
        return htmlContent;
    }

    @Override
    protected boolean isMatch(String content) {
        //24330165.html
        DomainCssSelector cssRecord = queryDomainCssSelector();
        String rules = cssRecord.getExtraRule();
        if(StringUtils.isNotEmpty(rules)) {
            JSONObject jsonRules = JSONObject.parseObject(rules);
            String isMatch = jsonRules.getString("match_pattern");
            if(StringUtils.isNotEmpty(isMatch)){
                //Pattern pattern = Pattern.compile("\\d+.html");
                Pattern pattern = Pattern.compile(isMatch);
                Matcher matcher = pattern.matcher(content);
                return matcher.find();
            }
        }
        return true;
    }

    private DomainCssSelector queryDomainCssSelector(){
        if(domainCssSelector==null){
            domainCssSelector = domainCssSelectorService.queryByDomainId(domainId);
            String rules = domainCssSelector.getExtraRule();
            if(StringUtils.isNotEmpty(rules)) {
                JSONObject jsonRules = JSONObject.parseObject(rules);
                this.jsonRules = jsonRules;
            }
        }
        return domainCssSelector;
    }
}
