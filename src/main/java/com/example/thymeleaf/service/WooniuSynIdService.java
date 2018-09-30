package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.WooniuSynIdMapper;
import com.example.thymeleaf.model.WooniuSynId;
import com.example.thymeleaf.model.WooniuSynIdExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by lixiaoming on 2018/9/27.
 */
@Service
public class WooniuSynIdService {

    private static final Integer COMPLETE = 1;
    private static final Integer NORMAL = 0;
    private static final Integer ERROR = 2;

    @Autowired
    private WooniuSynIdMapper wooniuSynIdMapper;

    public WooniuSynId getOneToBeSyn(){
        WooniuSynIdExample exmaple = new WooniuSynIdExample();
        WooniuSynIdExample.Criteria criteria = exmaple.createCriteria();
        criteria.andStatusEqualTo(NORMAL);
        exmaple.setOrderByClause("id");
        List<WooniuSynId> lst = wooniuSynIdMapper.selectByExample(exmaple);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }else{
            return null;
        }
    }

    public List<WooniuSynId> getToBeSyns(){
        WooniuSynIdExample exmaple = new WooniuSynIdExample();
        WooniuSynIdExample.Criteria criteria = exmaple.createCriteria();
        criteria.andStatusEqualTo(NORMAL);
        exmaple.setOrderByClause("id");
        List<WooniuSynId> lst = wooniuSynIdMapper.selectByExample(exmaple);
        return lst;
    }

    public void updateComplete(WooniuSynId wooniuSynId){
        wooniuSynId.setStatus(COMPLETE);
        wooniuSynIdMapper.updateByPrimaryKey(wooniuSynId);
    }

    public void updateError(WooniuSynId wooniuSynId){
        wooniuSynId.setStatus(ERROR);
        wooniuSynIdMapper.updateByPrimaryKey(wooniuSynId);
    }

    public void updatePos(WooniuSynId wooniuSynId,int pos){
        wooniuSynId.setPos(pos);
        wooniuSynIdMapper.updateByPrimaryKey(wooniuSynId);
    }

}
