package tech.adrianohrl.util;

import tech.adrianohrl.stile.exceptions.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class Calendars {
    
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String TIME_FORMAT = "HH:mm:ss";
    private final static DateFormat dateFormatter = new SimpleDateFormat(Calendars.DATE_FORMAT);
    private final static DateFormat timeFormatter = new SimpleDateFormat(Calendars.TIME_FORMAT);
    
    public static Calendar getTime(String time) throws IOException {
        Calendar timeCalendar = CalendarUtil.now();
        if (time == null || time.isEmpty()) {
            return timeCalendar;
        }
        try {
            timeCalendar.setTime(timeFormatter.parse(time));
        } catch (ParseException e) {
            throw new IOException("Wrong time format: " + e.getMessage());
        }
        return timeCalendar;
    }
    
    public static Calendar getDate(String date) throws IOException {
        Calendar dateCalendar = CalendarUtil.now();
        if (date == null || date.isEmpty()) {
            return dateCalendar;
        }
        try {
            dateCalendar.setTime(dateFormatter.parse(date));
        } catch (ParseException e) {
            throw new IOException("Wrong date format: " + e.getMessage());
        }
        return dateCalendar;
    }
    
    public static Calendar sum(String date, String time) throws IOException {
        return Calendars.sum(Calendars.getDate(date), Calendars.getTime(time));
    }
    
    public static Calendar sum(Calendar date, String time) throws IOException {
        return Calendars.sum(date, Calendars.getTime(time));
    }
    
    public static Calendar sum(String date, Calendar time) throws IOException {
        return Calendars.sum(Calendars.getDate(date), time);
    }
    
    public static Calendar sum(Date date, Date time) {
        Calendar calendarDate = CalendarUtil.now();
        calendarDate.setTime(date);
        Calendar calendarTime = CalendarUtil.now();
        calendarTime.setTime(time);
        return Calendars.sum(calendarDate, calendarTime);
    }
    
    public static Calendar sum(Calendar date, Calendar time) {
        Calendar calendar = CalendarUtil.now();
        calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, time.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, time.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));
        calendar.set(Calendar.AM_PM, time.get(Calendar.AM_PM));
        return calendar;
    }
    
    public static boolean changedDay(Date date1, Date date2) {
        Calendar calendar1 = CalendarUtil.now();
        calendar1.setTime(date1);
        Calendar calendar2 = CalendarUtil.now();
        calendar2.setTime(date2);
        return Calendars.changedDay(calendar1, calendar2);
    }
    
    public static boolean changedDay(Calendar calendar1, Calendar calendar2) {
        return calendar1 == null && calendar2 != null 
                || calendar1 != null && calendar2 == null 
                || calendar2 != null && calendar1 != null 
                    && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                    && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                    && calendar1.get(Calendar.DAY_OF_MONTH) != calendar2.get(Calendar.DAY_OF_MONTH);
    }
    
    public static boolean changedMonth(Date date1, Date date2) {
        Calendar calendar1 = CalendarUtil.now();
        calendar1.setTime(date1);
        Calendar calendar2 = CalendarUtil.now();
        calendar2.setTime(date2);
        return Calendars.changedMonth(calendar1, calendar2);
    }
    
    public static boolean changedMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1 == null && calendar2 != null 
                || calendar1 != null && calendar2 == null 
                || calendar2 != null && calendar1 != null 
                    && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                    && calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH);
    }
    
}
