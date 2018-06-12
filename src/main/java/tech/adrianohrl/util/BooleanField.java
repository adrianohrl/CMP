package tech.adrianohrl.util;

import java.text.ParseException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class BooleanField extends Field<Boolean> {
    
    private final String defaultTrue;

    public BooleanField(String title) {
        this(title, "y");
    }

    public BooleanField(String title, String defaultTrue) {
        super(title);
        this.defaultTrue = defaultTrue;
    }

    public BooleanField(String title, boolean mandatory) {
        this(title, "y", mandatory);
    }

    public BooleanField(String title, String defaultTrue, boolean mandatory) {
        super(title, mandatory);
        this.defaultTrue = defaultTrue;
    }

    @Override
    public void setValue(String value) throws ParseException {
        super.setValue(value.equalsIgnoreCase(defaultTrue) ? Boolean.TRUE : Boolean.FALSE);
    }
    
}
