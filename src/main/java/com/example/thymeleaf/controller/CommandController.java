package com.example.thymeleaf.controller;

import com.example.thymeleaf.service.SynBrotherChapterId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2018/9/30.
 */
@RestController
@RequestMapping("/command")
public class CommandController {
    private static Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    private SynBrotherChapterId synBrotherChapterId;

    @RequestMapping("/list")
    public List<String> list() {
        List<String> cmds  = new ArrayList<>();
        cmds.add("/sysn_brother_chapterid/{article_id}");
        return cmds;
    }

    /**
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/sysn_brother_chapterid/{article_id}",method= RequestMethod.GET, produces="application/json")
    public String sysnBrotherChapterId(@PathVariable("article_id") int articleId) {
        try{
            synBrotherChapterId.sysChapterId(articleId);
        }catch (Exception e){
            logger.warn("",e);
            return "ERROR";
        }
        return "OK";
    }
}
