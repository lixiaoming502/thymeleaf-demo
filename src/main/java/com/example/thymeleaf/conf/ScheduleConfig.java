package com.example.thymeleaf.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

//@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private static Log logger = LogFactory.getLog(ScheduleConfig.class);

    private final Map<Object, ScheduledFuture<?>> scheduledTasks =
            new IdentityHashMap<>();

 
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<IntervalTask> lst = taskRegistrar.getFixedDelayTaskList();
        taskRegistrar.setScheduler(taskExecutor());
    }
 
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
}