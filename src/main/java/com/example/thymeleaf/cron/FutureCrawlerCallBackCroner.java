package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.ApplicationContextProvider;
import com.example.thymeleaf.service.ArticleCallBack;
import com.example.thymeleaf.service.FutureCrawlerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 *
 * <p>回调调度器</p>
 */
@Component
public class FutureCrawlerCallBackCroner {
    private static Log logger = LogFactory.getLog(FutureCrawlerCallBackCroner.class);

    @Autowired
    private FutureCrawlerService futureCrawlerService;

    @Scheduled(fixedDelay = 10000)
    public void work(){
        logger.info("FutureCrawlerCallBackCroner start");
        List<FutureCrawler> toBeCalls = futureCrawlerService.getToBeCallBack();
        for(FutureCrawler futureCrawler:toBeCalls){
            callback(futureCrawler);
        }
        logger.info("FutureCrawlerCallBackCroner end");
    }

    private void callback(FutureCrawler futureCrawler) {
        int crawlerId = futureCrawler.getId();

        String beanName = futureCrawler.getCallbackBean();
        ArticleCallBack articleCallBack = (ArticleCallBack) ApplicationContextProvider.getBean(beanName);
        articleCallBack.callback(futureCrawler.getCallbackLevel(),crawlerId);

        futureCrawler.setCallbackStatus(1);
        futureCrawler.setUpdateTime(new Date());
        futureCrawlerService.update(futureCrawler);
    }

}
