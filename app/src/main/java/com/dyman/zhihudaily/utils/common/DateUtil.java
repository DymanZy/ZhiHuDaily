package com.dyman.zhihudaily.utils.common;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  时间工具类
 *
 *  @author dyman
 *  @since 2017/2/19 15:04
 */

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public final static String FORMAT_YEAR = "yyyy";

    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyy-MM-dd";

    public final static String FORMAT_TIME = "HH:mm";

    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";

    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd  HH:mm";

    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd  HH:mm";

    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd  HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    private static final int YEAR = 365 * 24 * 60 * 60;// 年

    private static final int MONTH = 30 * 24 * 60 * 60;// 月

    private static final int DAY = 24 * 60 * 60;// 日

    private static final int HOUR = 60 * 60;// 时

    private static final int MINUTE = 60;// 分


    /**
     *  根据时间戳获取描述性时间，如：3分钟前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {

        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;
        System.out.println("timeGap: "+timeGap);
        String timeStr;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {
            timeStr = timeGap / MINUTE + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }


    /**
     *  获取当前日期的制定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用制定的格式"yyyy-MM-dd HH:MM"
     */
    public static String getCurrentTime(String format) {

        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }


    /**
     *  date类型转换为String类型
     *
     * @param data Date类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String dateToString(Date data, String formatType) {

        return new SimpleDateFormat(formatType).format(data);
    }


    /**
     *  long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType 要转换的格式（yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日HH时mm分ss秒）
     * @return
     */
    public static String longToString(long currentTime, String formatType) {

        String strTime;
        // long类型转成Date类型
        Date date = longToDate(currentTime, formatType);
        // date类型转成String
        strTime = dateToString(date, formatType);
        return strTime;
    }


    /**
     *  long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType 要转换的格式（yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日HH时mm分ss秒）
     * @return
     */
    public static Date longToDate(long currentTime, String formatType) {

        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currentTime);
        // 把date类型的时间转换为string
        String sDateTime = dateToString(dateOld, formatType);
        // 把String类型转换为Date类型
        Date date = stringToDate(sDateTime, formatType);
        return date;
    }


    /**
     *  string类型转换为date类型（strTime的时间格式必须要与formatType的时间格式相同）
     *
     * @param strTime 要转换的string类型的时间
     * @param formatType 要转换的格式（yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日HH时mm分ss秒）
     * @return
     */
    public static Date stringToDate(String strTime, String formatType) {

        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    /**
     *  string类型转换为long类型（strTime的时间格式和formatType的时间格式必须相同）
     *
     * @param strTime 要转换的String类型的时间
     * @param formatType 时间格式
     * @return
     */
    public static long stringToLong(String strTime, String formatType) {

        // String类型转成date类型
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            return dateToLong(date);
        }
    }


    /**
     *  date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {

        return date.getTime();
    }

}
