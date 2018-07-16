package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.FutureCrawlerMapper;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerExample;
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
 * Created by lixiaoming on 2018/6/28.
 */
@Service
public class FutureCrawlerService {
    @Autowired
    private FutureCrawlerMapper futureCrawlerMapper ;

    @Deprecated
    public List<FutureCrawler> getToBeCrawl(){
        //使用分页插件,核心代码就这一行
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerStateNotEqualTo("F");
        //example.setOrderByClause("domain_id,id");
        return futureCrawlerMapper.selectByExample(example);
    }

    public int addNew(FutureCrawler futureCrawler){
        return futureCrawlerMapper.insert(futureCrawler);
    }

    public void update(FutureCrawler futureCrawler) {
        futureCrawlerMapper.updateByPrimaryKeySelective(futureCrawler);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void finish(int crawlerId, String crawlerState, String response) throws IOException {
        FutureCrawler futureCrawler = new FutureCrawler();
        futureCrawler.setId(crawlerId);
        futureCrawler.setUpdateTime(new Date());
        final String fileName = "data/crawler_" + crawlerId + ".txt";
        futureCrawler.setResponse(fileName);
        futureCrawler.setCrawlerState(crawlerState);
        futureCrawlerMapper.updateByPrimaryKeySelective(futureCrawler);

        FileWriter output = new FileWriter(fileName);
        output.write(response);
        output.close();

    }

    public FutureCrawler getToBeCrawlByDomainId(int domainId) {
        int pageNum = 1;
        int pageSize = 1;
        PageHelper.startPage(pageNum, pageSize);
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        List<String> stateLst = new ArrayList<>();
        stateLst.add("E");
        stateLst.add("F");
        criteria.andCrawlerStateNotIn(stateLst);
        criteria.andDomainIdEqualTo(domainId);
        example.setOrderByClause("id");
        List<FutureCrawler> lst = futureCrawlerMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lst)){
            return null;
        }else{
            return lst.get(0);
        }
    }

    public FutureCrawler queryByCrawlerId(int crawlerId) {
        return futureCrawlerMapper.selectByPrimaryKey(crawlerId);
    }

    public List<FutureCrawler> getToBeCallBack() {
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerStateEqualTo("F");
        criteria.andCallbackStatusEqualTo(0);
        example.setOrderByClause("id");
        List<FutureCrawler> lst = futureCrawlerMapper.selectByExample(example);
        return lst;
    }

    public int getWaitingLength() {
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerStateEqualTo("A");
        return futureCrawlerMapper.countByExample(example);
    }

    public int getMaxLen(Integer domainId) {
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        criteria.andDomainIdEqualTo(domainId);
        criteria.andCrawlerStateEqualTo("A");
        return futureCrawlerMapper.countByExample(example);
    }
}
