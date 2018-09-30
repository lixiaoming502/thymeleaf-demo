package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.BrotherChapterMapper;
import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.BrotherChapterExample;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/14.
 */
@Component
public class BrotherChapterService {
    @Autowired
    private BrotherChapterMapper brotherChapterMapper;

    public BrotherChapter queryByURL(String pageUrl) {
        BrotherChapterExample brotherChapterExample = new BrotherChapterExample();
        brotherChapterExample.createCriteria().andUrlEqualTo(pageUrl);
        List<BrotherChapter> lst = brotherChapterMapper.selectByExample(brotherChapterExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0);
        }
        return null;
    }

    public List<BrotherChapter> queryByBrotherId(int brotherId) {
        BrotherChapterExample brotherChapterExample = new BrotherChapterExample();
        brotherChapterExample.createCriteria().andBrotherIdEqualTo(brotherId);
        List<BrotherChapter> lst = brotherChapterMapper.selectByExample(brotherChapterExample);
        return lst;
    }

    public int getMaxSeqId(Integer brotherId) {
        int pageNum = 1;
        int pageSize = 1;
        PageHelper.startPage(pageNum, pageSize);
        BrotherChapterExample brotherChapterExample = new BrotherChapterExample();
        brotherChapterExample.createCriteria().andBrotherIdEqualTo(brotherId);
        brotherChapterExample.setOrderByClause("seq_id desc");
        List<BrotherChapter> lst = brotherChapterMapper.selectByExample(brotherChapterExample);
        if(CollectionUtils.isNotEmpty(lst)){
            return lst.get(0).getSeqId();
        }
        return 0;
    }

    public int addRecord(BrotherChapter chapter) {
        return brotherChapterMapper.insert(chapter);
    }

    public void update(BrotherChapter chapter){
        brotherChapterMapper.updateByPrimaryKey(chapter);
    }

    public List<BrotherChapter> queryByChapterId(int chapterId){
        BrotherChapterExample brotherChapterExample = new BrotherChapterExample();
        brotherChapterExample.createCriteria().andChapterIdEqualTo(chapterId);
        return brotherChapterMapper.selectByExample(brotherChapterExample);
    }
}
