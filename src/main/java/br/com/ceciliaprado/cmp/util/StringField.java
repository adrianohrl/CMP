/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.util;

import java.util.List;

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
