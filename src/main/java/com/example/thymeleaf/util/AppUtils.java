package com.example.thymeleaf.util;

import com.example.thymeleaf.crawler.AbstractCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(AbstractCrawler.class);

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
        try{
            int idx1 = content.indexOf("第");
            int idx2  = content.indexOf("章");
            if(idx1>-1&&idx2>0&&idx2>idx1){
                String hanzi = content.substring(idx1+1,idx2).trim();
                Integer ret = HanziConvert.convertFromHanzi(hanzi);
                if(ret!=null){
                    return ""+ret;
                }
            }else{
                return getPureNumbers(content);
            }
        }catch (Exception e){
            logger.warn("getNumbers ,content ["+content+"]");
            throw e;
        }
        return "";
    }

    public static String extractChapTitle(String title) {
        int index = title.indexOf(" ");
        String content =  title.substring(index+1);
        int idx1 = content.indexOf("第");
        int idx2  = content.indexOf("章");
        if(idx1>-1&&idx2>0&&idx2>idx1){
            content =  title.substring(idx2+1);
        }
        return content;
    }

    public static String getPureNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static  StringBuffer getStackBuffer(StackTraceElement[] stackElements){
        StringBuffer stackBuffer = new StringBuffer();
        if (stackElements != null) {
            //类名，方法名，行数
            for (int i = 0; i < stackElements.length; i++) {
                stackBuffer.append("\t"+stackElements[i].getClassName()+"|"+stackElements[i].getMethodName()+"|"+stackElements[i].getLineNumber()+"\n");
            }
        }
        return  stackBuffer;
    }



    public static void main(String[] args) {
        String input = "844章 派你做第一任爪哇总督";
        System.out.println(Integer.parseInt(AppUtils.getNumbers(input)));
    }
}
