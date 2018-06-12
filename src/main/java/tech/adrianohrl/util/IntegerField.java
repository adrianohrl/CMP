package tech.adrianohrl.util;

import java.text.ParseException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class IntegerField extends Field<Integer> {

    public IntegerField(String title) {
        super(title);
    }
    
    public IntegerField(String title, String value) {
        super(title, new Integer(value));
    }

    public IntegerField(String title, boolean mandatory) {
        super(title, mandatory);
    }
    
    public IntegerField(String title, String value, boolean mandatory) {
        super(title, new Integer(value), mandatory);
    }

    @Override
    public void setValue(String value) throws ParseException {
        super.setValue(!value.isEmpty() ? new Integer(value) : 0);
    }
    
}
