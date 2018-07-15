package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.BrotherMapper;
import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.BrotherExample;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/15.
 */
@Component
public class BrotherService {
    @Autowired
    private BrotherMapper brotherMapper;


    public Brother selectByBrotherId(int brotherId){
        return brotherMapper.selectByPrimaryKey(brotherId);
    }

    public List<Brother> selectByUpdateTurns(int updateTurns){
        PageHelper.startPage(1,10,false);
        BrotherExample brotherExample = new BrotherExample();
        brotherExample.createCriteria().andUpdateTurnsEqualTo(updateTurns);
        brotherExample.setOrderByClause("brother_id");
        List<Brother> lst = brotherMapper.selectByExample(brotherExample);
        return lst;
    }

    public int update(Brother record){
        return brotherMapper.updateByPrimaryKey(record);
    }

    public int insert(Brother brother) {
        return brotherMapper.insert(brother);
    }

    public Brother queryByURL(String pageUrl) {
        BrotherExample brotherExample = new BrotherExample();
        brotherExample.createCriteria().andBrotherUrlEqualTo(pageUrl);
        List<Brother> lst = brotherMapper.selectByExample(brotherExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }
        return null;
    }
}
