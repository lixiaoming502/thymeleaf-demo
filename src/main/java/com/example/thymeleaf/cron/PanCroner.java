package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.MyPan;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoming on 2018/7/5.
 */
@Component
public class PanCroner {

    private static Log logger = LogFactory.getLog(PanCroner.class);

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;
    @Autowired
    private MyPan myPan;

    //@Scheduled(fixedDelay = 1000)
    public void work(){
        logger.info("PanCroner start");
        List<FuturePageLoader> lst = futurePageLoaderService.getUnPaned();
        for(FuturePageLoader futurePageLoader:lst){
            pan(futurePageLoader);
        }
    }

    private void pan(FuturePageLoader futurePageLoader) {
        String path = futurePageLoader.getResponse();
        try {
            String content = FileUtils.readFileToString(new File(path),"UTF8");
            String key = futurePageLoader.getPageUrl();
            boolean paned = myPan.put(key,content);
            if(paned){
                futurePageLoader.setPaned("Y");
                logger.info("paned "+futurePageLoader.getId()+" success!");
            }else{
                futurePageLoader.setPaned("E");
            }
            futurePageLoader.setUpdateTime(new Date());
            futurePageLoaderService.update(futurePageLoader);

        } catch (IOException e) {
            logger.warn("",e);
        }
    }
}
