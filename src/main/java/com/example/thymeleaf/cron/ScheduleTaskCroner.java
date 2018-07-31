package com.example.thymeleaf.cron;

import com.example.thymeleaf.model.ScheduleTask;
import com.example.thymeleaf.service.ApplicationContextProvider;
import com.example.thymeleaf.service.ScheduleTaskService;
import com.example.thymeleaf.util.AppUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据t_schedule_task表，进行调度分配
 * Created by lixiaoming on 2018/7/13.
 */
@Component
public class ScheduleTaskCroner {

    private static Log logger = LogFactory.getLog(ScheduleTaskCroner.class);

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    private Executor executor = Executors.newFixedThreadPool(10);

    private HashSet<String> inProcessBeans = new HashSet<>();

    private ReentrantLock lock = new ReentrantLock();

    @Scheduled(fixedDelay = 1000)
    public void work(){
        logger.info("ScheduleTaskCroner entry");
        List<ScheduleTask> lst = scheduleTaskService.queryEnableTask();
        lst.stream().forEach(scheduleTask -> {
            Date last = scheduleTask.getLastUpdate();
            Integer fixDelay = scheduleTask.getFixDelay();
            Date now = new Date();
            if(last==null){
                last = now;
                scheduleTask.setLastUpdate(new Date());
                scheduleTaskService.update(scheduleTask);
            }
            Date next = AppUtils.addSecond(fixDelay,last);
            if(next.before(now)){
                executor.execute(new Thread(() -> {
                    String beanName = scheduleTask.getBeanName();
                    String beanMethod = scheduleTask.getBeanMethod();
                    Object bean = ApplicationContextProvider.getBean(beanName);
                    lock.lock();
                    try{
                        if(inProcessBeans.contains(beanName)){
                            return;
                        }
                        inProcessBeans.add(beanName);
                    }finally {
                        lock.unlock();
                    }

                    try {
                        Method method = bean.getClass().getMethod(beanMethod);
                        method.invoke(bean);
                    } catch (Exception e) {
                        logger.warn("",e);
                    }finally {
                        inProcessBeans.remove(beanName);
                        scheduleTask.setLastUpdate(new Date());
                        scheduleTaskService.update(scheduleTask);
                    }
                }));
            }
        });
        logger.info("ScheduleTaskCroner end");
    }

    public void removeInprocess(String beanName){
        inProcessBeans.remove(beanName);
    }
}
