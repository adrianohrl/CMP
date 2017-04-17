/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author adrianohrl
 */
public class CalendarField extends Field<Calendar> {
    
    private String dateFormat;

    public CalendarField(String title) {
        this(title, "hh:mm dd/MM/yyyy");
    }
    
    public CalendarField(String title, String dateFormat) {
        super(title);
        this.dateFormat = dateFormat;
    }

    public CalendarField(String title, boolean mandatory) {
        this(title, "hh:mm dd/MM/yyyy", mandatory);
    }
    
    public CalendarField(String title, String dateFormat, boolean mandatory) {
        super(title, mandatory);
        this.dateFormat = dateFormat;
    }

    @Override
    public void setValue(String value) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        calendar.setTime(formatter.parse(value));
        super.setValue(calendar);
    }
    
}
