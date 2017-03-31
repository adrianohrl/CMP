/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class CalendarFormat {
    
    private final static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private final static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss aaa");
    
    public static String formatDate(Calendar date) {
        return dateFormat.format(date);
    }
    
    public static String formatTime(Calendar time) {
        return timeFormat.format(time);
    }
    
    public static String format(Calendar calendar) {
        return CalendarFormat.format(calendar, " at ", true);
    }
    
    public static String format(Calendar calendar, String separator, boolean dateFirst) {
        if (dateFirst) {
            return dateFormat.format(calendar) + separator + timeFormat.format(calendar);
        }
        return timeFormat.format(calendar) + separator + dateFormat.format(calendar);
    }
    
}
