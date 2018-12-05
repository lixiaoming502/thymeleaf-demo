package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Brother;
import com.example.thymeleaf.model.BrotherChapter;
import com.example.thymeleaf.model.Chapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.similarity.JaccardSimilarity;
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
                int chapterId = findChapterId(brotherChapter.getTitle(),articleId,brotherChapter.getSeqId());
                brotherChapter.setChapterId(chapterId);
                brotherChapterService.update(brotherChapter);
            });
        });
    }

    public int findChapterId(String brotherTitle,int articleId,int seqId){
        String pureText = extractPureTxt(brotherTitle);
        //先根据seqId比较，文本相似度
        Chapter chapter = chapterService.queryByArticleIdSeqId(articleId,seqId);
        if(chapter!=null){
            String chpPureTitle = extractPureTxt(chapter.getTitle());
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            Double e = jaccardSimilarity.apply(pureText,chpPureTitle);
            logger.info("brotherTitle:"+brotherTitle+"check "+pureText+"->"+chpPureTitle);
            if(e>0.9){
                return chapter.getId();
            }
        }
        //尝试在seqId前后50的范围做文本匹配
        int start = 0;
        if(seqId>50){
            start = seqId-50;
        }
        List<Chapter> lst = chapterService.selectByArticleIdSeqId(articleId, start);
        for(Chapter chapter1:lst){
            String chpPureTitle = extractPureTxt(chapter1.getTitle());
            JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
            Double e = jaccardSimilarity.apply(pureText,chpPureTitle);
            if(e>0.9){
                logger.info("lst match brotherTitle:"+brotherTitle+"check "+pureText+"->"+chpPureTitle);
                return chapter1.getId();
            }
        }

        //match chapter_id,according to title
        //select like
        int chapterId = chapterService.selectLike(pureText,articleId);

        return chapterId;
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
