package aloha.shiningstarbase.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理相关的工具类
 * Created by chenmingzhen on 16-7-21.
 * //todo 添加时间格式化的方法，显示例如，昨天，早晨，等的显示方式
 */
public class TimeUtil {

    public static long timeToServer = 0;

    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_M = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_DATE = "yyyy年MM月dd日";
    private static final String TAG = TimeUtil.class.getSimpleName();

    /**
     * 获取当前时间的豪秒数
     *
     * @return
     */
    public static long getCurrentTimeInMillis() {
        return System.currentTimeMillis() - timeToServer;

    }

    /**
     * 获取一定天数之前的豪秒数
     *
     * @param day
     * @return
     */
    public static long getTimeInMillisDaysBefore(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, (0 - day));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取一定天数之后的豪秒数
     *
     * @param day
     * @return
     */
    public static long getTimeInMillisDaysAfter(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, (day));
        return calendar.getTimeInMillis();
    }

    /**
     * 返回豪秒的时间数
     *
     * @param millis
     * @return
     */
    public static String getStringForMillis(long millis, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return dateFormat.format(c.getTime());
    }

    public static int compareTo(long lhs, long rhs){
        Calendar cLhs = Calendar.getInstance();
        Calendar cRhs = Calendar.getInstance();
        cLhs.setTimeInMillis(lhs);
        cRhs.setTimeInMillis(rhs);
        return cLhs.compareTo(cRhs);
    }

    public static int compareToReverted(long lhs, long rhs){
        return compareTo(rhs, lhs);
    }

    /**
     * 返回该日期是星期几
     * @param millis
     * @return
     */
    public static String getWeekStr(long millis){
        Date date = new Date(millis);
        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * Created by Aloha <br>
     * -explain 当前日期Date
     * @Date 2016/11/15 12:02
     */
    public static String getNowDate(){
        return new SimpleDateFormat(YYYY_MM_DD_M).format(new java.util.Date());
    }

    /**
     * Created by Aloha <br>
     * -explain 当前日期Date精确到秒
     * @Date 2016/11/15 12:02
     */
    public static String getNowDateToSecond(){
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new java.util.Date());
    }

    /**
     * Created by Aloha <br>
     * -explain 毫秒数转 日期
     * @Date 2016/10/31 12:45
     */
    public static String milliSecondToDate(long time) {
        java.sql.Date d = new java.sql.Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_M);
        //tempDate = sdf.format(d);
        return sdf.format(d).substring(2);
    }

    /**
     * Created by Aloha <br>
     * -explain 字符串转日期
     * @Date 2016/10/31 12:45
     */
    public static String stringToDate(String time) {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(YYYYMMDDHHMMSS);
        Date date;
        long timeNow = 0;
        try {
            date = simpleDateFormat.parse(time);
            timeNow = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliSecondToDate(timeNow);
    }

    /**
     * Created by Aloha <br>
     * -explain 与当前时间相差 天数(卡券到期时间)
     * @Date 2016/10/31 12:45
     * @param date date日期字符串
     */
    public static String dateDifferNow(String date) {
        String cc = "20"+date;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_M);
        String now = new SimpleDateFormat(YYYY_MM_DD_M).format(new java.util.Date());
        long m = 0;
        try {
            m = sdf.parse(cc).getTime()-sdf.parse(now).getTime();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(m/(1000*60*60*24));
    }

    /**
     * Created by Aloha <br>
     * -explain
     * @Date 2017/1/12 18:56
     */
    public static String orderDateFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
        SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_M);
        Date date;
        String qq = "";
        try {
            date = sdf.parse(time);
            qq = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return qq;
    }
}
