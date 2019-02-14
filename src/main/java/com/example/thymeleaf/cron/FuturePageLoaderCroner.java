package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.FutureCrawlerCfg;
import com.example.thymeleaf.model.FuturePageLoader;
import com.example.thymeleaf.service.FutureCrawlerCfgService;
import com.example.thymeleaf.service.FuturePageLoaderService;
import com.example.thymeleaf.service.JoddHttp;
import com.example.thymeleaf.util.AppUtils;
import jodd.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * Created by lixiaoming on 2018/6/27.
 */
@Component
public class FuturePageLoaderCroner {

    private static final int MAX_TRY_TIMES = 3;
    private static Log logger = LogFactory.getLog(FuturePageLoaderCroner.class);

    @Autowired
    private FuturePageLoaderService futurePageLoaderService;

    @Autowired
    private JoddHttp joddHttp;

    @Autowired
    private FutureCrawlerCfgService futureCrawlerCfgService;

    private Executor executor = Executors.newFixedThreadPool(5);

    private Map<Integer,Integer> errorCountMap = new ConcurrentHashMap();

    //@Scheduled(fixedDelay = 1000)
    public void work(){
        logger.info("FuturePageLoaderCroner begin");
        //1.找到不同的domain_id
        List<FutureCrawlerCfg> lst = futureCrawlerCfgService.getAll();
        //2.对于每个domain_id,找到第一个最小记录，执行爬取
        ArrayList<FutureTask> futures = new ArrayList<>();
        for(FutureCrawlerCfg futureCrawlerCfg:lst){
            FuturePageLoader futurePageLoader = futurePageLoaderService.getToBeCrawlByDomainId(futureCrawlerCfg.getId());
            if(futurePageLoader!=null){
                Callable<Void> callable = () -> {
                    Date last = futureCrawlerCfg.getLastCrawlTime();
                    Integer gap = futureCrawlerCfg.getGap();
                    Date next  = AppUtils.addSecond(gap,last);
                    Date now = new Date();
                    if(next.before(now)){
                        crawl(futurePageLoader,futureCrawlerCfg.getCharset());
                        final Date afterCrawlerTime = new Date();
                        futureCrawlerCfg.setLastCrawlTime(afterCrawlerTime);
                        futureCrawlerCfgService.update(futureCrawlerCfg);
                    }
                    return null;
                };
                FutureTask<Void> futureTask = new FutureTask<>(callable);
                futures.add(futureTask);
                executor.execute(futureTask);
            }
        }
        futures.stream().forEach(futureTask -> {
            try {
                futureTask.get(3,TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                logger.warn("",e);
            } catch (ExecutionException e) {
                logger.warn("",e);
            } catch (TimeoutException e) {
                logger.warn("",e);
            }
        });
        logger.info("FuturePageLoaderCroner end");
    }

    private void crawl(FuturePageLoader futurePageLoader,String charset ) {
        logger.info("strat crawl ["+futurePageLoader.getPageUrl()+"]");
        try {
            HttpResponse response = joddHttp.sendBrowser(futurePageLoader.getPageUrl(),futurePageLoader.getPageUrl());
            int statusCode = response.statusCode();
            final Integer futurePageLoaderId = futurePageLoader.getId();
            if(statusCode!=200){
                logger.warn("get statusCode "+statusCode+" recrawl later");
               // response = joddHttp.sendBrowser(futurePageLoader.getPageUrl(),futurePageLoader.getPageUrl());
                //statusCode = response.statusCode();
                logger.warn(" response is  ["+response+"]");
                int count = 0;
                if(errorCountMap.get(futurePageLoaderId)!=null){
                    count = errorCountMap.get(futurePageLoaderId);
                }
                if(count<MAX_TRY_TIMES){
                    errorCountMap.put(futurePageLoaderId,count+1);
                    return;
                }else {
                    errorCountMap.remove(futurePageLoaderId);
                }
            }
            final byte[] bytes = response.bodyBytes();

            //实际读取的时候，有可能读不完整
            String length = response.contentLength();
            int requireLen = Integer.parseInt(length);
            int actLen = bytes.length;
            if(requireLen!=actLen){
                throw new Exception("requireLen!=actLen");
            }

            String body ;
            if(bytes!=null){
                body = new String(bytes,charset);
            }else{
                body = response.toString();
            }
            //更新状态，写文件，如果写文件异常则回滚
            futurePageLoaderService.loadComplete(futurePageLoaderId,statusCode,body);
        }catch (Exception e){
            logger.info("",e);
        }
    }

}
