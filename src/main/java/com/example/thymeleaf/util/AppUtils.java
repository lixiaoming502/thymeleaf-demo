package com.example.thymeleaf.util;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getCurrentDay(){
        SimpleDateFormat sd=new SimpleDateFormat("yyyyMMdd");
        return sd.format(new Date());
    }

    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static void main(String[] args) {
        String input = "第001章 被掠夺了";
        System.out.println(Integer.parseInt(AppUtils.getNumbers(input)));
    }
}
