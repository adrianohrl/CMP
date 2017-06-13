/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.util;

import br.com.ceciliaprado.cmp.exceptions.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author adrianohrl
 */
public class Calendars {
    
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String TIME_FORMAT = "HH:mm";
    private final static DateFormat dateFormatter = new SimpleDateFormat(Calendars.DATE_FORMAT);
    private final static DateFormat timeFormatter = new SimpleDateFormat(Calendars.TIME_FORMAT);
    
    public static Calendar getTime(String time) throws IOException {
        Calendar timeCalendar = new GregorianCalendar();
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
        Calendar dateCalendar = new GregorianCalendar();
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
    
    public static Calendar sum(Calendar date, Calendar time) {
        Calendar calendar = new GregorianCalendar();
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
    
}
