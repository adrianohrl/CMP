package tech.adrianohrl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CalendarField extends Field<Calendar> {
    
    private String dateFormat;

    public CalendarField(String title) {
        this(title, "HH:mm dd/MM/yyyy");
    }
    
    public CalendarField(String title, String dateFormat) {
        super(title);
        this.dateFormat = dateFormat;
    }

    public CalendarField(String title, boolean mandatory) {
        this(title, "HH:mm dd/MM/yyyy", mandatory);
    }
    
    public CalendarField(String title, String dateFormat, boolean mandatory) {
        super(title, mandatory);
        this.dateFormat = dateFormat;
    }

    @Override
    public void setValue(String value) throws ParseException {
        Calendar calendar = CalendarUtil.now();
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        calendar.setTime(formatter.parse(value));
        super.setValue(calendar);
    }

    @Override
    public String toString() {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(getValue().getTime());
    }
    
}
