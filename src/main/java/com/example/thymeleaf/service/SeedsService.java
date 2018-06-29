package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.SeedMapper;
import com.example.thymeleaf.model.Seed;
import com.example.thymeleaf.model.SeedExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixiaoming on 2018/6/28.
 */
@Service
public class SeedsService {
    @Autowired
    private SeedMapper seedMapper;


    public String getBeanName(int seedId) {
        Seed seed = seedMapper.selectByPrimaryKey(seedId);
        return seed.getParseClass();
    }
}
