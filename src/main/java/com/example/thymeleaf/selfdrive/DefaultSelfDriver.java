package com.example.thymeleaf.selfdrive;

import com.example.thymeleaf.cron.FuturePageLoaderCroner;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class DefaultSelfDriver implements SelfDriver {

    private static Logger logger = LoggerFactory.getLogger(FuturePageLoaderCroner.class);

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    @Override
    public DriverFuture fetch(DriverFuture driverFuture) {
        //根据ID，pageURL判断是否存在
        List<FuturePageLoader> lst = futurePageLoaderService.getByCrawlerId(driverFuture.getCrawlerId(),
                driverFuture.getPageUrl());
        if(CollectionUtils.isEmpty(lst)){
            //新增
            futurePageLoaderService.addRecord(driverFuture.getCrawlerId(),driverFuture.getPageUrl(),driverFuture.getDomainId());
        }else{
            //判断状态
            FuturePageLoader futurePageLoader = lst.get(0);
            if(futurePageLoader.getLoaderState().equals("F")) {
                File file = new File(futurePageLoader.getResponse());
                String content = null;
                try {
                    //final String charset = getCharset(driverFuture.getDomainId());
                    content = FileUtils.readFileToString(file, "UTF8");
                } catch (IOException e) {
                    logger.warn("",e);
                }
                driverFuture.setRespone(content);
                driverFuture.setStatusCode(futurePageLoader.getStateCode());
            }
        }
        return driverFuture;
    }

    private String getCharset(Integer domainId) {
        return futureCrawlerCfgService.queryByDomainId(domainId).getCharset();
    }
}
