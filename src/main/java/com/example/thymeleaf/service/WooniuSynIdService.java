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
    //在原status基础上加10，表示已经做过清除crawler.txt的操作
    private static final Integer CLEAR = 10;
    private static final Integer ERROR = 2;

    @Autowired
    private WooniuSynIdMapper wooniuSynIdMapper;

    public void add(WooniuSynId wooniuSynId){
        wooniuSynIdMapper.insert(wooniuSynId);
    }

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

    public WooniuSynId selectByArticleId(int articleId){
        WooniuSynIdExample exmaple = new WooniuSynIdExample();
        WooniuSynIdExample.Criteria criteria = exmaple.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        List<WooniuSynId> lst = wooniuSynIdMapper.selectByExample(exmaple);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }else{
            return null;
        }
    }


    public WooniuSynId getOneToBeClear(){
        WooniuSynIdExample exmaple = new WooniuSynIdExample();
        WooniuSynIdExample.Criteria criteria = exmaple.createCriteria();
        criteria.andStatusEqualTo(COMPLETE);
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

    public void updateClear(WooniuSynId wooniuSynId){
        wooniuSynId.setStatus(wooniuSynId.getStatus()+CLEAR);
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
