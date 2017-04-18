/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import java.text.ParseException;

/**
 *
 * @author adrianohrl
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
