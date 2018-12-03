package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.DomainCssSelectorMapper;
import com.example.thymeleaf.model.DomainCssSelector;
import com.example.thymeleaf.model.DomainCssSelectorExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixiaoming on 2018/11/30.
 */
@Service
public class DomainCssSelectorService {

    @Autowired
    private DomainCssSelectorMapper domainCssSelectorMapper;

    public int insert(DomainCssSelector domainCssSelector){
        return domainCssSelectorMapper.insert(domainCssSelector);
    }

    public DomainCssSelector queryByDomainId(int domainId){
        DomainCssSelectorExample domainCssSelectorExample = new DomainCssSelectorExample();
        domainCssSelectorExample.createCriteria().andDomainIdEqualTo(domainId);
        List<DomainCssSelector> lst = domainCssSelectorMapper.selectByExample(domainCssSelectorExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }else{
            return null;
        }
    }
}
