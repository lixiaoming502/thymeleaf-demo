package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.BrotherChapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixiaoming on 2018/9/30.
 *
 * <p>同步t_brother_chapter的chapter_id域</>
 */
@Service
public class SynBrotherChapterId {

    private static Log logger = LogFactory.getLog(SynBrotherChapterId.class);

    @Autowired
    private BrotherService brotherService;

    @Autowired
    private BrotherChapterService brotherChapterService;

    @Autowired
    private ChapterService chapterService;

    public void sysChapterId(int articleId){
        logger.info("sysChapterId "+articleId);
        List<Brother> lst = brotherService.selectByArticleId(articleId);
        lst.forEach(brother -> {
            List<BrotherChapter> brotherChapters = brotherChapterService.queryByBrotherId(brother.getBrotherId());
            brotherChapters.forEach(brotherChapter -> {
                int chapterId = findChapterId(brotherChapter.getTitle(),articleId);
                brotherChapter.setChapterId(chapterId);
                brotherChapterService.update(brotherChapter);
            });
        });
    }

    private int findChapterId(String brotherTitle,int articleId){
        //match chapter_id,according to title
        //select like
        String pureText = extractPureTxt(brotherTitle);
        int chapterId = chapterService.selectLike(pureText,articleId);
        return chapterId;

    }

    private String extractPureTxt(String brotherTitle) {
        //第040章一秒不想看到
        int idx1 = brotherTitle.indexOf("第");
        int idx2 = brotherTitle.indexOf("章");
        if(idx1>-1&&idx2>idx1){
            return brotherTitle.substring(idx2+1).trim();
        }else{
            return brotherTitle;
        }
    }
}
