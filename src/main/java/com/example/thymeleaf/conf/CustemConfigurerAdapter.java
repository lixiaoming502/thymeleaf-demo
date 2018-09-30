package com.example.thymeleaf.conf;

import com.example.thymeleaf.filter.CommandFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义配置项类，该类中和存入拦截器、过滤器等配置项信息
 * @author Administrator
 */
@Configuration
public class CustemConfigurerAdapter {

    @Bean
    public FilterRegistrationBean authFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("authFilter");
        CommandFilter authFilter = new CommandFilter();
        registrationBean.setFilter(authFilter);
        registrationBean.setOrder(1);
        List<String> urlList = new ArrayList<String>();
        urlList.add("/command/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }

}

