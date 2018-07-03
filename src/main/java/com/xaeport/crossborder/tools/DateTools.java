package com.xaeport.crossborder.tools;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间转换工具类
 * Created by xcp on 2017/7/26.
 */
public class DateTools {
    private static final Logger logger = Logger.getLogger(DateTools.class);

    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateTimeZoneFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateTimeStr17Format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static SimpleDateFormat dateTimeStr14Format = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getDateTimeString(Date date) {
        return dateTimeFormat.format(date);
    }

    public static String getDateTimeStr17String(Date date) {
        return dateTimeStr17Format.format(date);
    }

    public static String getDateTimeStr14String(Date date) {
        return dateTimeStr14Format.format(date);
    }

    public static Date parseDateTimeString(String datetime) throws ParseException {
        return dateTimeFormat.parse(datetime);
    }

    public static String parseDateTimeZoneString(Date date, String zone) throws ParseException {
        TimeZone timeZone = TimeZone.getTimeZone(zone);
        return dateTimeZoneFormat.format(date) + timeZone.getID().replace("GMT", "");
    }

    public static String getShortDateString(Date date) {
        return shortDateFormat.format(date);
    }

    public static Date shortDateTimeString(String datetime) throws ParseException {
        return shortDateFormat.parse(datetime);
    }

    public static String getDateString(Date date) {
        return dateFormat.format(date);
    }

    public static String getDateStringToString(String dateStr) {
        String str = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
        return str;
    }

//    public static void main(String[] args) throws ParseException {
//        String a = parseDateTimeZoneString(new Date(), "GMT+0");
//        System.out.println(a);
//    }

}
