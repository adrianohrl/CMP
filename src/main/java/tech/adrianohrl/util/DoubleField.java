package tech.adrianohrl.util;

import java.text.ParseException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class DoubleField extends Field<Double> {

    public DoubleField(String title) {
        super(title);
    }

    public DoubleField(String title, String value) {
        super(title, new Double(value));
    }

    public DoubleField(String title, boolean mandatory) {
        super(title, mandatory);
    }

    public DoubleField(String title, String value, boolean mandatory) {
        super(title, new Double(value), mandatory);
    }

    @Override
    public void setValue(String value) throws ParseException {
        super.setValue(new Double(value));
    }
    
}
