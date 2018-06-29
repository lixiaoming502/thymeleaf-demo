package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.FutureCrawlerMapper;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FutureCrawlerExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/28.
 */
@Service
public class FutureCrawlerService {
    @Autowired
    private FutureCrawlerMapper futureCrawlerMapper ;

    public List<FutureCrawler> getToBeCrawl(){
        //使用分页插件,核心代码就这一行
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        FutureCrawlerExample example = new FutureCrawlerExample();
        FutureCrawlerExample.Criteria criteria = example.createCriteria();
        criteria.andCrawlerStateNotEqualTo("F");
        return futureCrawlerMapper.selectByExample(example);
    }

    public void update(FutureCrawler futureCrawler) {
        futureCrawlerMapper.updateByPrimaryKeySelective(futureCrawler);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void finish(int crawlerId, String response) throws IOException {
        FutureCrawler futureCrawler = new FutureCrawler();
        futureCrawler.setId(crawlerId);
        futureCrawler.setUpdateTime(new Date());
        final String fileName = "data/crawler_" + crawlerId + ".txt";
        futureCrawler.setResponse(fileName);
        futureCrawler.setCrawlerState("F");
        futureCrawlerMapper.updateByPrimaryKeySelective(futureCrawler);

        FileWriter output = new FileWriter(fileName);
        output.write(response);
        output.close();

    }
}
