package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.ChapterMapper;
import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.ChapterExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
