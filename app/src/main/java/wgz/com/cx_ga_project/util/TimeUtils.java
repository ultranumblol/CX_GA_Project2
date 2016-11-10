package wgz.com.cx_ga_project.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/8/18.
 */

public class TimeUtils {
    private static final String TAG = "TimeUtils";

    public static final int SECOND = 1000;
    public static final int MINUTES = SECOND * 60;
    public static final int HOUR = MINUTES * 60;
    public static final int DAY = HOUR * 24;
    public static final int WEEK = DAY * 7;

    /**
     * 返回用户友好的时间差
     *
     * @param httpTime    来自网络数据的时间
     * @param currentTime 当前时间
     * @return
     */
    public static String getTimeDifference(int httpTime, long currentTime) {

        long hTime = ((long) httpTime) * 1000;
        long dTime = currentTime - hTime;
        LogUtil.d("hTime=" + hTime + " currentTime=" + currentTime + " dTime=" + dTime);

        if (dTime < MINUTES) {
            return dTime / SECOND + "秒前";
        } else if (dTime < HOUR) {
            return dTime / MINUTES + "分钟前";
        } else if (dTime < DAY) {
            return dTime / HOUR + "小时前";
        } else if (dTime < WEEK) {
            return dTime / DAY + "天前";
        } else {
            return DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).format(new Date(hTime));
        }

    }
    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
}
