package com.ly.milestone.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lyc28724 on 2017/4/7.
 */
public abstract class DateUtils {

    private DateUtils() {

    }

    /**
     * Default time format :  yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time format :  yyyy-MM-dd HH:mm
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_CN_FORMAT = "yyyy年MM月dd日 HH:mm";
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Default month format
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * Default day format
     */
    public static final String DAY_FORMAT = "dd";


    //Date pattern,  demo:  2013-09-11
    public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";

    public static boolean isDate(String dateAsText) {
        return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches(DATE_PATTERN);
    }

    public static Date now() {
        return new Date();
    }

    public static String toDateText(Date date) {
        return toDateText(date, DATE_FORMAT);
    }

    public static String toDateText(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date getDate(String dateText) {
        return getDate(dateText, DATE_FORMAT);
    }


    public static Date getDate(String dateText, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", e);
        }
    }

    public static String toDateTime(Date date) {
        return toDateText(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String toCNDateTime(Date date) {
        return toDateText(date, DATE_TIME_CN_FORMAT);
    }


    /**
     * Return current year.
     *
     * @return Current year
     */
    public static int currentYear() {
        return calendar().get(Calendar.YEAR);
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    public static long getRemainingTimeOfToday() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        Calendar todayCurrent = Calendar.getInstance();
        return (todayEnd.getTimeInMillis() - todayCurrent.getTimeInMillis()) / 1000;
    }


}
