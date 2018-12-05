package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.ChapterMapper;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.ChapterExample;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/12.
 */
@Component
public class ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;

    public int getMaxSeqId(int articleId){
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria condition = chapterExample.createCriteria();
        condition.andArtileIdEqualTo(articleId);
        chapterExample.setOrderByClause("seq_id desc");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        if(CollectionUtils.isEmpty(lst)){
            return 0;
        }else{
            return lst.get(0).getSeqId();
        }
    }

    public int addRecord(Chapter chapter) {
        return chapterMapper.insert(chapter);
    }

    public int getMaxArticlId(){
        int pageNum = 1;
        int pageSize = 1;
        PageHelper.startPage(pageNum, pageSize);
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("artile_id desc");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        if(CollectionUtils.isEmpty(lst)){
            return 0;
        }else{
            return lst.get(0).getArtileId();
        }
    }

    public List<Chapter> selectByArticleId(int articleId) {
        int pageNum = 1;
        int pageSize = 100;
        PageHelper.startPage(pageNum, pageSize);
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andArtileIdEqualTo(articleId);
        chapterExample.setOrderByClause("id ");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        return lst;
    }

    public List<Chapter> selectAllByArticleId(int articleId) {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andArtileIdEqualTo(articleId);
        chapterExample.setOrderByClause("id ");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        return lst;
    }

    public List<Chapter> selectByArticleIdSeqId(int articleId,int seqId) {
        int pageNum = 1;
        int pageSize = 100;
        PageHelper.startPage(pageNum, pageSize);
        ChapterExample chapterExample = new ChapterExample();
        final ChapterExample.Criteria criteria = chapterExample.createCriteria();
        criteria.andArtileIdEqualTo(articleId);
        criteria.andSeqIdGreaterThan(seqId);
        chapterExample.setOrderByClause("seq_id ");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        return lst;
    }

    public List<Chapter> getToBeCrawl() {
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andCollectFlagEqualTo(false);
        chapterExample.setOrderByClause("artile_id,seq_id,id ");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        return lst;
    }

    public int selectLike(String pureText, int articleId) {
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria condition = chapterExample.createCriteria();
        condition.andArtileIdEqualTo(articleId);
        condition.andTitleLike(pureText);
        chapterExample.setOrderByClause("id desc");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        if(CollectionUtils.isNotEmpty(lst)&&lst.size()==1){
            return lst.get(0).getId();
        }
        return 0;
    }

    public void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public Chapter queryByLocalUrl(String localUrl) {
        int pageNum = 1;
        int pageSize = 1;
        PageHelper.startPage(pageNum, pageSize);
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andLocalUrlEqualTo(localUrl);
        chapterExample.setOrderByClause("id ");
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        return lst.get(0);
    }

    public Chapter queryById(Integer refId) {
        return chapterMapper.selectByPrimaryKey(refId);
    }

    public void delete(Integer id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

    public Chapter queryByArticleIdSeqId(int articleId, int seqId) {
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria condition = chapterExample.createCriteria();
        condition.andArtileIdEqualTo(articleId);
        condition.andSeqIdEqualTo(seqId);
        List<Chapter> lst = chapterMapper.selectByExample(chapterExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }
        return null;
    }
}
