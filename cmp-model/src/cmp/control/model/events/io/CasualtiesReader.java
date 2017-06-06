/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.events.io;

import cmp.exceptions.IOException;
import cmp.model.events.Casualty;
import cmp.util.AbstractReader;
import cmp.util.BooleanField;
import cmp.util.Field;
import cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class CasualtiesReader extends AbstractReader<Casualty> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = "Name";
    private final static String COLLECTIVE_COLUMN_TITLE = "Collective";
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new BooleanField(COLLECTIVE_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Casualty build(List<Field> fields) throws IOException {
        String name = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        boolean collective = Field.getFieldValue(fields, COLLECTIVE_COLUMN_TITLE);
        return new Casualty(name, collective);
    }
    
}
