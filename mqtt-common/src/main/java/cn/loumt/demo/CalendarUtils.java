package cn.loumt.demo;

import cn.loumt.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();

        //Calendar -> Date
        Date time = calendar.getTime();

        String time2Date = DateUtils.DateToStr(time,DateUtils.DATE);
        String time2DateWithTime = DateUtils.DateToStr(time,DateUtils.DATE_WITH_TIME);

        int hourOfDate = DateUtils.getHourOfDate(time);
        System.out.println("hourOfDate:" + hourOfDate);

        System.out.println(time2Date);
        System.out.println(time2DateWithTime);
        System.out.println(time);
        System.out.println(calendar);
    }


}
