package tech.adrianohrl.util;

import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class StringField extends Field<String> {
    
    public StringField(String title) {
        super(title);
    }

    public StringField(String title, String value) {
        super(title, value);
    }

    public StringField(String title, String value, List<String> validValues) {
        super(title, value, validValues);
    }
    
    public StringField(String title, boolean mandatory) {
        super(title, mandatory);
    }
    
    public StringField(String title, boolean mandatory, List<String> validValues) {
        super(title, mandatory, validValues);
    }

    public StringField(String title, String value, boolean mandatory) {
        super(title, value, mandatory);
    }

    public StringField(String title, String value, boolean mandatory, List<String> validValues) {
        super(title, value, mandatory, validValues);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }
    
}
