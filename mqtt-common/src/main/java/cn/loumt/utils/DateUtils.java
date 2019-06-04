package cn.loumt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String DATE_FORMAT_YM = "YYYY-MM";

    public static final String DATE = "yyyy-MM-dd";

    public static final String DATE_WITH_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_WITH_TIME_NO_SECOND = "yyyy-MM-dd HH:mm";

    public static final String DATE_NOSPLIT = "yyyyMMdd";

    public static final String DATE_MONTH_DAY = "M月d日";

    public static final String HOUR_MINUTE = "HH:mm";

    public static final String DATE_WITH_TIME_NO_SECOND_CN = "yyyy年MM月dd日 HH:mm";

    public static String DateToHHmm(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(HOUR_MINUTE);
        return sdf.format(d);
    }

    public static String DateToShortStr(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_MONTH_DAY);
        return sdf.format(d);
    }

    public static String DateToStr(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_WITH_TIME);
        return sdf.format(d);
    }

    public static String DateToStr(Date d, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(d);
    }

    public static Date strToDate(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_WITH_TIME);
        return sdf.parse(d);
    }

    public static String DateToStr_NoSeconds(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_WITH_TIME_NO_SECOND);
        return sdf.format(d);
    }

    public static String DateToStr_NoSeconds_CN(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_WITH_TIME_NO_SECOND_CN);
        return sdf.format(d);
    }

    public static Date strToDate_NoSeconds(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_WITH_TIME_NO_SECOND);
        return sdf.parse(d);
    }

    public static String noSplitDateToDate(String d) throws ParseException {
        SimpleDateFormat sdf_no = new SimpleDateFormat(DATE_NOSPLIT);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        Date date = sdf_no.parse(d);
        return sdf.format(date);
    }

    public static String noSplitDateToStr(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_NOSPLIT);
        return sdf.format(d);
    }

    public static Date strToNosplitDate(String d) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_NOSPLIT);
        return sdf.parse(d);
    }

    public static String getDayFromDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        return sdf.format(d);
    }

    public static String DateToStrNoTime(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        return sdf.format(d);

    }

    public static String getWeekBeginDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE); // 设置时间格式

        Calendar cal = Calendar.getInstance();

        cal.setTime(d);

        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        String imptimeBegin = sdf.format(cal.getTime());

        return imptimeBegin;
    }

    public static String getWeekEndDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE); // 设置时间格式

        Calendar cal = Calendar.getInstance();

        cal.setTime(d);

        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

        cal.add(Calendar.DATE, 8 - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        String imptimeBegin = sdf.format(cal.getTime());

        return imptimeBegin;
    }

    public static String getMonthBeginDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE); // 设置时间格式

        Calendar cal = Calendar.getInstance();

        cal.setTime(d);

        int day = cal.get(Calendar.DAY_OF_MONTH);

        cal.add(Calendar.DATE, -day + 1);

        String imptimeBegin = sdf.format(cal.getTime());

        return imptimeBegin;
    }

    /**
     * 返回当前时间和参数时间相差的天-时-分
     *
     * @param d
     * @return
     */
    public static String differTime(Date d) {
        long differMills = Math.abs(System.currentTimeMillis() - d.getTime());
        long oneDayTime = 24 * 3600 * 1000;
        long oneHourTime = 3600 * 1000;
        long oneMinuteTime = 60 * 1000;
        StringBuffer sb = new StringBuffer();

        long dayNum = differMills / oneDayTime;
        if (dayNum > 0) {
            sb.append(dayNum + "天 ");
        }
        differMills = differMills % oneDayTime;
        long hourNum = differMills / oneHourTime;
        if (hourNum > 0 || dayNum > 0) {
            sb.append(hourNum + "小时 ");
        }
        differMills = differMills % oneHourTime;
        long minuteNum = differMills / oneMinuteTime;
        if (minuteNum > 0 || dayNum > 0 || hourNum > 0) {
            sb.append(minuteNum + "分钟");
        }
        return sb.toString();
    }

    /**
     * 返回当前时间和参数时间相差的天数
     *
     * @param date
     * @return
     */
    public static long differDays(Date date) {
        long oneDayTime = 24 * 3600 * 1000;
        long differMills = Math.abs(System.currentTimeMillis() - date.getTime());
        return differMills / oneDayTime;
    }

    /**
     * 返回两个时间参数相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long differDays(Date startDate, Date endDate) {
        long oneDayTime = 24 * 3600 * 1000;
        long differMills = Math.abs(endDate.getTime() - startDate.getTime());
        return differMills / oneDayTime;
    }

    /**
     * 返回当前时间和参数时间相差的分钟数
     *
     * @param date
     * @return
     */
    public static long differMins(Date date) {
        long differMills = Math.abs(System.currentTimeMillis() - date.getTime());
        long oneDayTime = 24 * 3600 * 1000;
        long oneHourTime = 3600 * 1000;
        long oneMinuteTime = 60 * 1000;
        differMills = differMills % oneDayTime % oneHourTime;
        return differMills / oneMinuteTime;
    }

    /**
     * 返回当前时间和参数时间相差的小时数
     *
     * @param date
     * @return
     */
    public static long differHours(Date date) {
        long oneDayTime = 24 * 3600 * 1000;
        long oneHourTime = 3600 * 1000;
        long differMills = Math.abs(System.currentTimeMillis() - date.getTime());
        differMills = differMills % oneDayTime;
        return differMills / oneHourTime;
    }

    /**
     * 获取当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        // 一周第一天是否为星期天
        boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
        // 获取周几
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        // 若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 获取当前日期小时
     */
    public static int getHourOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        // 获取小时
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取当前日期分
     *
     * @param dt
     * @return
     */
    public static int getMinuteOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        // 获取分
        int minute = cal.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 获取所在周星期几的具体日期
     */
    public static String convertWeekByDate(Date time, int weekNum) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        // String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, weekNum);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期几的日期：" + imptimeEnd);
        return imptimeEnd;

    }

    /**
     * 日期增加天数
     *
     * @param date 日期
     * @param days 增加天数
     * @return
     */
    public static Date addDays(Date date, int days) {
        /*
         * Calendar calendar = Calendar.getInstance();
         * calendar.add(Calendar.DAY_OF_YEAR, days); Date now =
         * calendar.getTime(); return now;
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
        return calendar.getTime();

    }

    /**
     * 日期增加月数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 日期增加秒数
     *
     * @param date   日期
     * @param amount 秒数
     * @return
     */
    public static Date addSecond(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 增加日期属性值
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    public static Date add(final Date date, final int calendarField,
                           final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        Date now = new Date();
        return now;
    }
}
