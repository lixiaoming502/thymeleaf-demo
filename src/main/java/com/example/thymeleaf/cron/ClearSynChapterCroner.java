package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.Chapter;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.model.WooniuSynId;
import com.example.thymeleaf.service.ChapterService;
import com.example.thymeleaf.service.FutureCrawlerService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.WooniuSynIdService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by lixiaoming on 2018/10/11.
 * 根据t_wooniu_syn的pos位置，删除已经同步过的chapter采集内容
 */
@Component
public class ClearSynChapterCroner {
    private static Log logger = LogFactory.getLog(ClearSynChapterCroner.class);

    @Autowired
    private WooniuSynIdService wooniuSynIdService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    public void work() {
        WooniuSynId record = wooniuSynIdService.getOneToBeClear();
        if(record==null){
            logger.info("no article need to clear");
            return;
        }
        final Integer articleId = record.getArticleId();
        logger.info("ClearSynChapterCroner article_id "+ articleId);
        List<Chapter> lst = chapterService.selectByArticleId(articleId);
        int maxSeqId = record.getPos();
        for(Chapter chapter:lst){
            clearPageCrawler(chapter.getId());
            Integer seqId = chapter.getSeqId();
            if(seqId<=maxSeqId){
                String localUrl = chapter.getLocalUrl();
                if(StringUtils.isEmpty(localUrl)||!localUrl.startsWith("data")){
                    logger.warn("localUrl非法,"+localUrl);
                    continue;
                }
                deleteFile(localUrl);
            }
        }
        wooniuSynIdService.updateClear(record);
    }

    private void deleteFile(String localUrl) {
        File file = new File(localUrl);
        if(file.exists()){
            logger.info("delete file "+localUrl);
            file.delete();
        }
    }

    private void clearPageCrawler(Integer id) {
       //删除t_future_crawler采集的txt文本和t_future_page_loader采集到的文本
        FutureCrawler record = futureCrawlerService.queryByRefId(id);
        if(record!=null){
            deleteFile(record.getResponse());
            List<FuturePageLoader> lst = futurePageLoaderService.getByCrawlerId(record.getId());
            for(FuturePageLoader futurePageLoader:lst){
                deleteFile(futurePageLoader.getResponse());
            }
        }
    }
}
