/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author adrianohrl
 */
public class Calendars {
    
    public static Calendar sum(Calendar date, Calendar time) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, time.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, time.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));
        return calendar;
    }
    
}
