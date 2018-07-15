package com.example.thymeleaf.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lixiaoming on 2018/7/14.
 */
public interface CallBack {
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor=Exception.class)
    public void callback(int level,int crawlerId);
}
