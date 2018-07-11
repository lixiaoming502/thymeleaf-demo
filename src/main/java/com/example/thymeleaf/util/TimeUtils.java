package com.example.thymeleaf.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
    public static final String DATE_FORMAT_NO_SPLIT = "yyyyMMdd";
    public static final String DATE_FORMAT_WITH_LINE = "yyyy-MM-dd";
    public static final String FULL_TIME_FORMAT_NO_SPLIT = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_WITH_COLON = "HH:mm:ss";

    public TimeUtils() {
    }

    public static String getDateFromCalendar(Calendar ca) {
        Date d = ca.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        return sd.format(d);
    }

    public static String getFormatDate(Date date, String format) {
        if(StringUtils.isEmpty(format)) {
            format = "yyyyMMdd";
        }

        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }

    public static boolean isSameDay(Date lastUpdate, Date curDate) {
        String s1 = getDate(lastUpdate, "yyyyMMdd");
        String s2 = getDate(curDate, "yyyyMMdd");
        return s1.compareTo(s2) == 0;
    }

    public static boolean isSameDay(Timestamp lastUpdate, Timestamp curDate) {
        long d1 = lastUpdate.getTime();
        long d2 = curDate.getTime();
        Date d11 = new Date(d1);
        Date d22 = new Date(d2);
        return isSameDay(d11, d22);
    }

    public static int getHourOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(11);
    }

    public static Date addMinute(int toAdd, Date dateTime) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(dateTime);
        ca.add(12, toAdd);
        return ca.getTime();
    }

    public static String getCurDay() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        return sd.format(new Date());
    }

    public static String getCurDay(String slit) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy" + slit + "MM" + slit + "dd");
        return sd.format(new Date());
    }

    public static Date addSeconds(int toAdd, Date dateTime) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(dateTime);
        ca.add(13, toAdd);
        return ca.getTime();
    }

    public static String getYesterday() {
        Calendar ca = new GregorianCalendar();
        ca.add(5, -1);
        return getDateFromCalendar(ca);
    }

    public static String addDays(String startDay, String format, int inc) throws ParseException {
        Date date = (new SimpleDateFormat(format)).parse(startDay);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, inc);
        return (new SimpleDateFormat(format)).format(cal.getTime());
    }

    public static String getDate(Date date, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(7) - 1;
        if(w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static int getMinute(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(12);
    }

    public static Date parseToDate(String dtAcct, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dtAcct);
    }

    public static String getDayTime(Date date, String format) {
        if(StringUtils.isEmpty(format)) {
            format = "yyyyMMddHHmmss";
        }

        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }
}