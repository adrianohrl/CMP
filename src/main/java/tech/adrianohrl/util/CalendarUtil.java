package tech.adrianohrl.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CalendarUtil {
    
    private static final Locale LOCALE = PropertyUtil.getDefaultLocale();
    private static final long DAYS_TO_MILLISECONDS = 24 * 60 * 60; // [h/day] * [min/h] * [s/min]
    private static final long MONTHS_TO_MILLISECONDS = 30 * 24 * 60 * 60; // [day/month] * [h/day] * [min/h] * [s/min]
    private static final long YEARS_TO_MILLISECONDS = 12 * 30 * 24 * 60 * 60; // [month/year] * [day/month] * [h/day] * [min/h] * [s/min]
    
    public static int getDaysBetween(Calendar startDateInclusive, Calendar endDateExclusive) {
        return (int) Math.ceil(getDifference(startDateInclusive, endDateExclusive) / DAYS_TO_MILLISECONDS);
    }
    
    public static int getMonthsBetween(Calendar startDateInclusive, Calendar endDateExclusive) {
        return (int) Math.ceil(getDifference(startDateInclusive, endDateExclusive) / MONTHS_TO_MILLISECONDS);
    }
    
    public static int getYearsBetween(Calendar startDateInclusive, Calendar endDateExclusive) {
        return (int) Math.ceil(getDifference(startDateInclusive, endDateExclusive) / YEARS_TO_MILLISECONDS);
    }
    
    private static double getDifference(Calendar startDateInclusive, Calendar endDateExclusive) {
        return (endDateExclusive.getTimeInMillis() - startDateInclusive.getTimeInMillis()) / 1000.0;        
    }
    
    public static LocalDate toLocalDate(Calendar calendar) {
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
    
    public static Calendar now() {
        return new GregorianCalendar(LOCALE);
    }
    
}
