package io.itpl.microservice.utils;




import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    public static String trim(Date date, TimeZone zone){
        Calendar timer = Calendar.getInstance(zone);

        timer.setTimeInMillis(date.getTime());
        int year = timer.get(Calendar.YEAR);
        int month = timer.get(Calendar.MONTH);
        int day = timer.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year,month+1,day).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String monthOf(Date date,TimeZone zone){
        Calendar timer = Calendar.getInstance(zone);
        timer.setTime(date);
        int month = timer.get(Calendar.MONTH);
        int year = timer.get(Calendar.YEAR);
        String monthName = DateHelper.month(month);
        return monthName+"-"+year;
    }
    public static String today(TimeZone zone){
        Calendar timer = Calendar.getInstance(zone);
        timer.set(Calendar.HOUR_OF_DAY,0);
        timer.set(Calendar.MINUTE,0);
        timer.set(Calendar.SECOND,0);
        timer.set(Calendar.MILLISECOND,0);
        int year = timer.get(Calendar.YEAR);
        int month = timer.get(Calendar.MONTH);
        int day = timer.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year,month+1,day).format(DateTimeFormatter.ISO_LOCAL_DATE);

    }
    public static String today(String timeZoneId){
        TimeZone zone = (!Strings.isNullOrEmpty(timeZoneId))?TimeZone.getTimeZone(timeZoneId):TimeZone.getDefault();
        return today(zone);
    }
    public  static String today(){
        TimeZone zone = TimeZone.getDefault();
        return today(zone);
    }
    public static String currentMonth(TimeZone zone){
        Calendar timer = Calendar.getInstance(zone);
        int month = timer.get(Calendar.MONTH);
        int year = timer.get(Calendar.YEAR);
        String monthName = month(month);
        return monthName+"-"+year;
    }
    public static String currentMonth(String timeZoneId){
        TimeZone zone = (!Strings.isNullOrEmpty(timeZoneId))?TimeZone.getTimeZone(timeZoneId):TimeZone.getDefault();
        return currentMonth(zone);
    }
    public static String currentMonth(){
        TimeZone zone = TimeZone.getDefault();
        return currentMonth(zone);
    }

    /**
     *  Generate Time-stamp in yyyyMMWeek format.
     * @return
     */
    public static long yyyyMMWeek() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int week = today.get(Calendar.WEEK_OF_MONTH);
        String monthStr = month < 10 ? "0"+month : ""+month;
        String weekStr = "0"+week;
        String prefix = String.format("%d%s%s",year,monthStr,weekStr);
        return Long.valueOf(prefix);
    }

    /**
     * String value of Weekday from the Calender.WeekDay index.
     * @param day
     * @return
     */
    public static String weekday(int day) {
        switch(day) {
            case Calendar.SUNDAY:
                return ("SUN");
            case Calendar.MONDAY:
                return("MON");

            case Calendar.TUESDAY:
                return("TUE");

            case Calendar.WEDNESDAY:
                return("WED");

            case Calendar.THURSDAY:
                return ("THU");

            case Calendar.FRIDAY:
                return("FRI");

            case Calendar.SATURDAY:
                return("SAT");
            default:
                return "UNKNOWN";
        }
    }
    /**
     * String value of Month based on Month Number.
     * @param day
     * @return
     */
    public static String month(int day) {
        switch(day) {
            case Calendar.JANUARY:
                return ("JAN");
            case Calendar.FEBRUARY:
                return("FEB");

            case Calendar.MARCH:
                return("MAR");

            case Calendar.APRIL:
                return("APR");

            case Calendar.MAY:
                return ("MAY");

            case Calendar.JUNE:
                return("JUN");

            case Calendar.JULY:
                return("JUL");

            case Calendar.AUGUST:
                return("AUG");

            case Calendar.SEPTEMBER:
                return("SEP");

            case Calendar.OCTOBER:
                return("OCT");

            case Calendar.NOVEMBER:
                return("NOV");

            case Calendar.DECEMBER:
                return("DEC");
            default:
                return "UNKNOWN";
        }
    }
}
