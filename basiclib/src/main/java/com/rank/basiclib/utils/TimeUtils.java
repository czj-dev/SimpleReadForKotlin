package com.rank.basiclib.utils;

import android.annotation.SuppressLint;
import android.util.Pair;

import com.rank.basiclib.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;


/**
 * Created by jks27 on 2018/3/12.
 */

public class TimeUtils {
    private static SimpleDateFormat format;

    /**
     * 获取格式化的时间
     *
     * @param time 时间
     * @return
     */
    public static String getTimeFAT(long time, String type) {
        final long labelTime = time * 1000;
        String text = compareTime(time, type, labelTime);
        return text;
    }

    public static String compareTime(long time, String type, long labelTime) {
        String text = "";
        Date timeDate = new Date(System.currentTimeMillis());

        long timeGap = timeDate.getTime() - labelTime;
        long H = 1000;
        long Min = H * 60;
        long Hour = Min * 60;
        long Day = Hour * 24;

        if (timeGap < Hour) {//小时以内
            text = (timeGap / Min) + "分钟前";
        } else if (timeGap < Day) {//一天以内
            text = (timeGap / Hour) + "小时前";
        } else if (timeGap < 2 * Day) {//两天
            text = "昨天" + " " + getDateToString(time, type);
        } else {//2天以上
            text = getDateToString(time, type);
        }
        return text;
    }

    /**
     * 判断时间距离现在时间的差距
     *
     * @param labelTime 目标时间
     * @return first : 时间单位    second :  具体时间数值
     */
    public static Pair<TimeUnit, Long> compareTime(long labelTime) {
        Date timeDate = new Date(System.currentTimeMillis());

        long timeGap = timeDate.getTime() - labelTime;
        long H = 1000;
        long Min = H * 60;
        long Hour = Min * 60;
        long Day = Hour * 24;
        if (timeGap < Hour) {//小时以内
            return Pair.create(TimeUnit.MINUTES, timeGap / Min);
        } else if (timeGap < Day) {//一天以内
            return Pair.create(TimeUnit.HOURS, timeGap / Hour);
        } else {
            return Pair.create(TimeUnit.DAYS, timeGap / Day);
        }
    }

    // 时间戳转换成字符串
    @SuppressLint("SimpleDateFormat")
    public static String getDateToString(long time, String type) {
        Date d = new Date(time * 1000);
        format = new SimpleDateFormat(type);
        return format.format(d);
    }

    /**
     * 按照时间格式给出一个时间的描述。比如“2014年8月17日 09:50”
     *
     * @param time
     * @param pattern 时间的格式。如果为null，则默认使用
     *                “yyyy-MM-dd HH:mm”
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedTimeStr(long time, String pattern) {
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat(pattern != null ? pattern : "yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static Date parseTime(String time) {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            Timber.tag(Constant.TAG).e(e);
            return new Date();
        }
        return date;
    }
}
