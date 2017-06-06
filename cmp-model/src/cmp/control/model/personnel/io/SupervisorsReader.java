/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.personnel.io;

import cmp.exceptions.IOException;
import cmp.model.personnel.Manager;
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
public class SupervisorsReader extends AbstractReader<Manager> {
    
    /** Column Titles **/
    private final static String MANAGER_COLUMN_TITLE = "Manager";
    private final static String SUPERVISOR_COLUMN_TITLE = "Supervisor";
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(MANAGER_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SUPERVISOR_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Manager build(List<Field> fields) throws IOException {
        String managerName = Field.getFieldValue(fields, MANAGER_COLUMN_TITLE);
        boolean containsManager = contains(managerName);
        String supervisorName = Field.getFieldValue(fields, SUPERVISOR_COLUMN_TITLE);
        Supervisor supervisor = getSupervisor(supervisorName);
        if (supervisor == null) {
            throw new IOException(supervisorName + " supervisor is not registered yet!!!");
        }
        Manager manager = !containsManager ? getManager(managerName) : get(managerName);
        if (manager == null) {
            throw new IOException(managerName + " manager is not registered yet!!!");
        }
        if (!manager.getSupervisors().contains(supervisor)) {
            manager.getSupervisors().add(supervisor);
        } else {
            return null;
        }
        return !containsManager ? manager : null;
    }
    
    protected Manager getManager(String managerName) {
        return new Manager("", "", "", managerName);
    }
    
    protected Supervisor getSupervisor(String supervisorName) {
        return new Supervisor("", "", "", supervisorName);
    }
    
    protected boolean contains(String managerName) {
        for (Manager manager : this) {
            if (managerName.equalsIgnoreCase(manager.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected Manager get(String managerName) {
        for (Manager manager : this) {
            if (managerName.equalsIgnoreCase(manager.getName())) {
                return manager;
            }
        }
        return null;
    }
    
}
