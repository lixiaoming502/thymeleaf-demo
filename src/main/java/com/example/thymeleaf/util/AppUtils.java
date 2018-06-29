package com.example.thymeleaf.util;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lixiaoming on 2018/6/28.
 */
public class AppUtils {
    public  static Date addSecond(int toAdd, Date dateTime) {
        Calendar ca=new GregorianCalendar();
        ca.setTime(dateTime);
        ca.add(Calendar.SECOND, toAdd);
        return ca.getTime();
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String extraDomain(String pageUrl) throws MalformedURLException {
        java.net.URL  url = new  java.net.URL(pageUrl);
        String host = url.getHost();// 获取主机名
        return host;
    }
}
