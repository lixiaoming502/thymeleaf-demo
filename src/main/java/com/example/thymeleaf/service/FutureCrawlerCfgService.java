package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.FutureCrawlerCfgMapper;
import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FutureCrawlerCfgExample;
import org.apache.commons.collections.CollectionUtils;
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

    public FutureCrawlerCfg queryByActiveDomain(String domain){
        FutureCrawlerCfgExample example = new FutureCrawlerCfgExample();
        FutureCrawlerCfgExample.Criteria criteria = example.createCriteria();
        criteria.andDomainNameEqualTo(domain);
        criteria.andEnableEqualTo("Y");
        final List<FutureCrawlerCfg> futureCrawlerCfgs = futureCrawlerCfgMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(futureCrawlerCfgs)){
            return null;
        }
        return futureCrawlerCfgs.get(0);
    }

    public int insert(FutureCrawlerCfg futureCrawlerCfg){
        return futureCrawlerCfgMapper.insert(futureCrawlerCfg);
    }

    public FutureCrawlerCfg queryByDomain(String domain){
        FutureCrawlerCfgExample example = new FutureCrawlerCfgExample();
        FutureCrawlerCfgExample.Criteria criteria = example.createCriteria();
        criteria.andDomainNameEqualTo(domain);
        final List<FutureCrawlerCfg> futureCrawlerCfgs = futureCrawlerCfgMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(futureCrawlerCfgs)){
            return null;
        }
        return futureCrawlerCfgs.get(0);
    }

    public FutureCrawlerCfg queryByDomainId(int domainId){
        FutureCrawlerCfgExample example = new FutureCrawlerCfgExample();
        FutureCrawlerCfgExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(domainId);
        final List<FutureCrawlerCfg> futureCrawlerCfgs = futureCrawlerCfgMapper.selectByExample(example);
        return futureCrawlerCfgs.get(0);
    }
}
