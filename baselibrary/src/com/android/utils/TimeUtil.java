package com.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * @author sunjuncai
 *
 */
public class TimeUtil {
    
    /**
     * 获取当前时间
     * @param formatStr
     * @return
     */
    public static String getToday(String formatStr) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        return formatter.format(currentTime);
    }
    
    /**
     * 获取当前时间
     * @param formatStr
     * @return
     */
    public static String getToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }


    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }
    /**
     *  得到当前月
     */
    public static String getMonth(){
    	Calendar cal = Calendar.getInstance();
    	String month = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
    	return month;
    }
    
    /**
     *  得到上月
     */
    public static String getLastMonth(){
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.MONTH, -1);
    	String month = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
    	return month;
    }
    
    
    
    /**
     * 得到本周周一
     * 
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(c.getTime());
    }

    /**
     * 得到本周周日
     * 
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(c.getTime());
    }

    /**
     * 得到本月第一天的日期
     * 
     * @Methods Name getFirstDayOfMonth
     * @return Date
     */
    public static String getFirstDayOfMonth() {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(new Date());
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(cDay.getTime());
    }

    /**
     * 得到本月最后一天的日期
     * 
     * @Methods Name getLastDayOfMonth
     * @return Date
     */
    public static String getLastDayOfMonth() {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(new Date());
        cDay.set(Calendar.DAY_OF_MONTH,cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        return df2.format(cDay.getTime());
    }
    
    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        //把日期设置为当月第一天
        c.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        c.roll(Calendar.DATE, -1);
        int maxDate = c.get(Calendar.DATE);
        return maxDate;
    }
    
    /**
     * 判断时间date1是否在时间date2之前
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(date1).before(sdf.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据提供的格式转换
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        String formatTime = null;
        try {
            formatTime = sdf2.format(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            formatTime = time;
        }
        return formatTime;
    }
    

}
