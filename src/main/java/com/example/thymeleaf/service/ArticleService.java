package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.ArticleMapper;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.ArticleExample;
import com.example.thymeleaf.util.AppUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/12.
 */
@Component
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public int addRecord(Article record){
        return articleMapper.insert(record);
    }

    public Article queryByListURL(String listURL){
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria condition = example.createCriteria();
        condition.andListUrlEqualTo(listURL);
        List<Article> lst = articleMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(lst)){
            return lst.get(0);
        }
        return null;
    }

    public Article selectByPrimary(int articleId){
        return articleMapper.selectByPrimaryKey(articleId);
    }

    public int update(Article article) {
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    public List<Article> getToBeUpdate() {
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria condition = example.createCriteria();
        condition.andModifyDateIsNull();
        List<Article> lst = articleMapper.selectByExample(example);
        return lst;
    }

    public List<Article> getNoBrothers() {
        int pageNum = 1;
        int pageSize = 2;
        PageHelper.startPage(pageNum, pageSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria condition = example.createCriteria();
        condition.andBrotherStateIsNull();
        List<Article> lst = articleMapper.selectByExample(example);
        return lst;
    }

    public List<Article> getToBeUpdate(int maxtArticleId) {
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria condition = example.createCriteria();
        condition.andIdGreaterThan(maxtArticleId);
        List<Article> lst = articleMapper.selectByExample(example);
        return lst;
    }
}
