/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.personnel.io;

import cmp.exceptions.IOException;
import cmp.model.personnel.Subordinate;
import cmp.model.personnel.Supervisor;
import cmp.util.AbstractReader;
import cmp.util.Field;
import cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class SubordinatesReader extends AbstractReader<Supervisor> {
    
    /** Column Titles **/
    private final static String SUPERVISOR_COLUMN_TITLE = "Supervisor";
    private final static String SUBORDINATE_COLUMN_TITLE = "Subordinate";
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(SUPERVISOR_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SUBORDINATE_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Supervisor build(List<Field> fields) throws IOException {
        String supervisorName = Field.getFieldValue(fields, SUPERVISOR_COLUMN_TITLE);
        boolean containsSupervisor = contains(supervisorName);
        String subordinateName = Field.getFieldValue(fields, SUBORDINATE_COLUMN_TITLE);
        Subordinate subordinate = getSubordinate(subordinateName);
        if (subordinate == null) {
            throw  new IOException(subordinateName + " subordinate is not registered!!!");
        }
        Supervisor supervisor = !containsSupervisor ? getSupervisor(supervisorName) : get(supervisorName);
        if (supervisor == null) {
            throw new IOException(supervisorName + " supervisor is not registered yet!!!");
        }
        if (supervisor.getSubordinates().contains(subordinate)) {
            return null;
        }
        supervisor.getSubordinates().add(subordinate);
        return !containsSupervisor ? supervisor : null;
    }
    
    protected Supervisor getSupervisor(String supervisorName) {
        return new Supervisor("", "", "", supervisorName);
    }
    
    protected Subordinate getSubordinate(String subordinateName) {
        return new Subordinate("", subordinateName);
    }
    
    protected boolean contains(String supervisorName) {
        for (Supervisor supervisor : getReadEntities()) {
            if (supervisorName.equalsIgnoreCase(supervisor.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected Supervisor get(String supervisorName) {
        for (Supervisor supervisor : getReadEntities()) {
            if (supervisorName.equalsIgnoreCase(supervisor.getName())) {
                return supervisor;
            }
        }
        return null;
    }
    
}
