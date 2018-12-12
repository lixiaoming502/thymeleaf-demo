package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.FutureCrawlerCfgMapper;
import com.example.thymeleaf.dao.FuturePageLoaderMapper;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FutureCrawlerCfgExample;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.model.FuturePageLoaderExample;
import com.example.thymeleaf.util.AppUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Service
public class FuturePageLoaderService {
    @Autowired
    private FuturePageLoaderMapper futurePageLoaderMapper ;

    @Autowired
    private FutureCrawlerCfgMapper futureCrawlerCfgMapper;

    public List<FuturePageLoader> getToBeLoaded() {
        //使用分页插件,核心代码就这一行
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andLoaderStateEqualTo("A");
        return futurePageLoaderMapper.selectByExample(example);
    }

    public List<FuturePageLoader> getUnPaned() {
        //使用分页插件,核心代码就这一行
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andLoaderStateEqualTo("F");
        criteria.andPanedIsNull();
        criteria.andStateCodeEqualTo(200);

        return futurePageLoaderMapper.selectByExample(example);
    }

    public void update(FuturePageLoader futurePageLoader){
        futurePageLoaderMapper.updateByPrimaryKeySelective(futurePageLoader);
    }


    /**
     * 获取下次爬取时间=上次爬取时间+间隔
     * 更新下次爬取时间，更新爬取状态
     * @param domainName
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void updateCrawlState(String domainName, Integer id) {
        FutureCrawlerCfgExample example = new FutureCrawlerCfgExample();
        FutureCrawlerCfgExample.Criteria criteria = example.createCriteria();
        criteria.andDomainNameEqualTo(domainName);
        List<FutureCrawlerCfg> lst = futureCrawlerCfgMapper.selectByExample(example);
        FutureCrawlerCfg futureCrawlerCfg = lst.get(0);
        Date last = futureCrawlerCfg.getLastCrawlTime();
        Integer gap = futureCrawlerCfg.getGap();
        Date next  = AppUtils.addSecond(gap,last);
        Date now = new Date();
        if(next.before(now)){
            next = now;
        }
        futureCrawlerCfg.setLastCrawlTime(next);
        futureCrawlerCfgMapper.updateByPrimaryKey(futureCrawlerCfg);

        FuturePageLoader record = new FuturePageLoader();
        record.setId(id);
        record.setLoaderState("P");
        record.setPlanTime(next);
        record.setUpdateTime(new Date());
        futurePageLoaderMapper.updateByPrimaryKeySelective(record);
    }

    public List<FuturePageLoader> getToBeCrawler() {
        //使用分页插件,核心代码就这一行
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andPlanTimeLessThanOrEqualTo(new Date());
        criteria.andPlanTimeIsNotNull();
        criteria.andLoaderStateEqualTo("P");
        return futurePageLoaderMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void loadComplete(Integer id, int statusCode, String body) throws IOException {
        FuturePageLoader record = new FuturePageLoader();
        record.setId(id);
        record.setStateCode(statusCode);
        final String fileName = "data/" + id + ".txt";
        record.setResponse(fileName);
        if(statusCode==200){
            record.setLoaderState("F");
        }else{
            record.setLoaderState("E");
        }
        record.setUpdateTime(new Date());
        futurePageLoaderMapper.updateByPrimaryKeySelective(record);

        FileWriter output = new FileWriter(fileName);
        output.write(body);
        output.close();
    }

    public List<FuturePageLoader> getByCrawlerId(int crawlerId, String pageUrl) {
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerIdEqualTo(crawlerId);
        criteria.andPageUrlEqualTo(pageUrl);
        return futurePageLoaderMapper.selectByExample(example);
    }

    public void addRecord(int crawlerId, String pageUrl, Integer domainId) {
        FuturePageLoader futurePageLoader = new FuturePageLoader();
        futurePageLoader.setLoaderState("A");
        futurePageLoader.setCrawlerId(crawlerId);
        futurePageLoader.setPageUrl(pageUrl);
        final Date createTime = new Date();
        futurePageLoader.setCreateTime(createTime);
        futurePageLoader.setUpdateTime(createTime);
        futurePageLoader.setDomainId(domainId);

        futurePageLoaderMapper.insert(futurePageLoader);
    }

    public boolean isCrawlerPageWaiting(int crawlerId){
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerIdEqualTo(crawlerId);
        //criteria.andLoaderStateNotEqualTo("F");
        List<String> values = new ArrayList<>();
        values.add("E");
        values.add("F");
        criteria.andLoaderStateNotIn(values);
        List<FuturePageLoader> lst = futurePageLoaderMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(lst);
    }

    public FuturePageLoader getToBeCrawlByDomainId(Integer domainId) {
        int pageNum = 1;
        int pageSize = 1;
        PageHelper.startPage(pageNum, pageSize);
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andDomainIdEqualTo(domainId);
        criteria.andLoaderStateEqualTo("A");
        example.setOrderByClause("crawler_id,id");
        List<FuturePageLoader> lst = futurePageLoaderMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lst)){
            return null;
        }else{
            return lst.get(0);
        }
    }

    public List<FuturePageLoader> getByCrawlerId(int crawlerId) {
        FuturePageLoaderExample example = new FuturePageLoaderExample();
        FuturePageLoaderExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerIdEqualTo(crawlerId);
        return futurePageLoaderMapper.selectByExample(example);
    }
}

