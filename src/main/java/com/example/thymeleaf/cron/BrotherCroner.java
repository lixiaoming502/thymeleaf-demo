package com.example.thymeleaf.cron;

import com.example.thymeleaf.dao.BrotherMapper;
import com.example.thymeleaf.model.Article;
import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.service.ArticleService;
import com.example.thymeleaf.service.SearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/13.
 */
@Component
public class BrotherCroner {

    //private static Log logger = LogFactory.getLog(BrotherCroner.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BrotherMapper brotherMapper;

    @Scheduled(fixedDelay = 3000)
    public void work(){
        List<Article> articles = articleService.getNoBrothers();
        for(Article article:articles){
            String title = article.getTitle();
            List<String> urls = searchService.searchAll(title);
            if(CollectionUtils.isEmpty(urls)){
                article.setBrotherState("E");
            }else{
                for(String url:urls){
                    Brother brother = new Brother();
                    brother.setArticleId(article.getId());
                    brother.setBrotherUrl(url);
                    brotherMapper.insert(brother);
                }
                article.setBrotherState("F");
            }
            articleService.update(article);
        }
    }
}
