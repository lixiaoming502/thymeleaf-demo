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
}
