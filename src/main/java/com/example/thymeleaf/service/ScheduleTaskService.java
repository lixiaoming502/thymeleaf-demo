package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.ScheduleTaskMapper;
import com.example.thymeleaf.model.ScheduleTask;
import com.example.thymeleaf.model.ScheduleTaskExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lixiaoming on 2018/7/20.
 */
@Component
public class ScheduleTaskService {
    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    public List<ScheduleTask> queryEnableTask() {
        ScheduleTaskExample scheduleTaskExample = new ScheduleTaskExample();
        scheduleTaskExample.createCriteria().andEnableEqualTo(true);
        return scheduleTaskMapper.selectByExample(scheduleTaskExample);
    }

    public int update(ScheduleTask scheduleTask) {
        return scheduleTaskMapper.updateByPrimaryKey(scheduleTask);
    }
}
