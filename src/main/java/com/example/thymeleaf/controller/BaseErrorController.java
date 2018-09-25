package com.example.thymeleaf.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "error")
public class BaseErrorController implements ErrorController {
    private static Logger logger = LoggerFactory.getLogger(BaseErrorController.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        logger.info("出错啦！进入自定义错误控制器");
        return "error";
    }
    @RequestMapping
    public String error(HttpServletRequest request,Exception e) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String,Object> errorAttributesData = errorAttributes.getErrorAttributes(requestAttributes,true);
        Integer status=(Integer)errorAttributesData.get("status");  //状态码
        String path=(String)errorAttributesData.get("path");        //请求路径
        String messageFound=(String)errorAttributesData.get("message");   //异常信息

        JSONObject reData = new JSONObject();
        reData.put("status_", status);
        reData.put("path_", path);
        reData.put("message", messageFound);
        logger.info(reData.toJSONString());

        logger.warn("访问["+path+"]的时候出错了，",e);
        return getErrorPath();
    }

    //@RequestMapping
    //@ResponseBody
    public JSONObject doHandleError(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String,Object> errorAttributesData = errorAttributes.getErrorAttributes(requestAttributes,true);
        Integer status=(Integer)errorAttributesData.get("status");  //状态码
        String path=(String)errorAttributesData.get("path");        //请求路径
        String messageFound=(String)errorAttributesData.get("message");   //异常信息

        JSONObject reData = new JSONObject();
        reData.put("status_", status);
        reData.put("path_", path);
        reData.put("message", messageFound);
        logger.info(reData.toJSONString());
        return reData;
    }


}