package tech.adrianohrl.stile.control.model.personnel.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SupervisorsReader extends AbstractReader<Manager> {
    
    /** Column Titles **/
    private final static String MANAGER_COLUMN_TITLE = PropertyUtil.getSupervisorColumnTitle("Manager");
    private final static String SUPERVISOR_COLUMN_TITLE = PropertyUtil.getSupervisorColumnTitle("Supervisor");
    
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
        if (manager.getSupervisors().contains(supervisor)) {
            return null;
        }
        manager.getSupervisors().add(supervisor);
        return !containsManager ? manager : null;
    }
    
    protected Manager getManager(String managerName) {
        return new Manager("", "", "", managerName);
    }
    
    protected Supervisor getSupervisor(String supervisorName) {
        return new Supervisor("", "", "", supervisorName);
    }
    
    protected boolean contains(String managerName) {
        for (Manager manager : getReadEntities()) {
            if (managerName.equalsIgnoreCase(manager.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected Manager get(String managerName) {
        for (Manager manager : getReadEntities()) {
            if (managerName.equalsIgnoreCase(manager.getName())) {
                return manager;
            }
        }
        return null;
    }
    
}
