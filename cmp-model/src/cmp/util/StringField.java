/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

/**
 *
 * @author adrianohrl
 */
public class StringField extends Field<String> {
    
    public StringField(String title) {
        super(title);
    }

    public StringField(String title, String value) {
        super(title, value);
    }
    
    public StringField(String title, boolean mandatory) {
        super(title, mandatory);
    }

    public StringField(String title, String value, boolean mandatory) {
        super(title, value, mandatory);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }
    
}
