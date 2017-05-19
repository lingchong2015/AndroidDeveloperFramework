package com.curry.stephen.lcandroidlib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lingchong on 16/6/23.
 */
public class DateTimeHelper {

    public static String getDateTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(Calendar.getInstance().getTime());
    }

    public static String getDateNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String textNow = simpleDateFormat.format(new Date());
        return textNow;
    }

    public static String convertToDateByFormatter(String myDate, String sourceFormatter, String destinationFormatter) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(sourceFormatter, Locale.CHINESE);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(destinationFormatter, Locale.CHINESE);
        try {
            Date date = simpleDateFormat1.parse(myDate);
            return simpleDateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTickNow() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * yyyy-MM-dd hh:mm:ss => hh.xx(分与秒转换为十进制).
     */
    public static float getTime(String textDateTime) {
        try {
            String[] sArray = textDateTime.split(" ");
            String[] timeArray = sArray[1].split(":");
            float hour = Float.parseFloat(timeArray[0]);
            double minute = Float.parseFloat(timeArray[1]);
            double second = Float.parseFloat(timeArray[2]);
            String textResult = String.format("%.2f", hour + minute / 60.0f + second / 3600.0f);
            return Float.parseFloat(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getHour(String textDateTime, String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        try {
            Date datetime = simpleDateFormat.parse(textDateTime);
            return datetime.getHours();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 小时、分、秒=>总秒数.
     */
    public static float getSeconds(String textDateTime) {
        try {
            String[] sArray = textDateTime.split(" ");
            String[] timeArray = sArray[1].split(":");
            return Float.parseFloat(timeArray[0]) * 3600 + Float.parseFloat(timeArray[1]) * 60 + Float.parseFloat(timeArray[2]) ;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long addSeconds(long seconds) {
        return System.currentTimeMillis() + (seconds * 1000);
    }
}
