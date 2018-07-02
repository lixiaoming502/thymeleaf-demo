package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.FutureCrawlerCfgMapper;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FutureCrawlerCfgExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixiaoming on 2018/6/29.
 */
@Service
public class FutureCrawlerCfgService {
    @Autowired
    private FutureCrawlerCfgMapper futureCrawlerCfgMapper;

    public List<FutureCrawlerCfg> getAll(){
        FutureCrawlerCfgExample example = new FutureCrawlerCfgExample();
        return futureCrawlerCfgMapper.selectByExample(example);
    }

    public void update(FutureCrawlerCfg futureCrawlerCfg) {
        futureCrawlerCfgMapper.updateByPrimaryKey(futureCrawlerCfg);
    }
}
