package com.example.thymeleaf.util;

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
}
