/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.personnel.io;

import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.util.AbstractReader;
import br.com.ceciliaprado.cmp.util.Field;
import br.com.ceciliaprado.cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class SectorsReader extends AbstractReader<Sector> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = "Name";
    private final static String SUPERVISOR_COLUMN_TITLE = "Supervisor";

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SUPERVISOR_COLUMN_TITLE, false));
        return defaultFields;
    }
    
    @Override
    protected Sector build(List<Field> fields) throws IOException {
        String supervisorName = Field.getFieldValue(fields, SUPERVISOR_COLUMN_TITLE);
        Supervisor supervisor = getSupervisor(supervisorName);
        if (supervisor == null) {
            throw new IOException(supervisorName + " supervisor is not registered yet!!!");
        }
        return new Sector(Field.getFieldValue(fields, NAME_COLUMN_TITLE), supervisor);
    }
    
    protected Supervisor getSupervisor(String supervisorName) throws IOException {
        return new Supervisor("", "", "", supervisorName);
    }

}
