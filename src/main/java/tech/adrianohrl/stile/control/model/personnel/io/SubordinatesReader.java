package tech.adrianohrl.stile.control.model.personnel.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Subordinate;
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
public class SubordinatesReader extends AbstractReader<Supervisor> {
    
    /** Column Titles **/
    private final static String SUPERVISOR_COLUMN_TITLE = PropertyUtil.getSubordinateColumnTitle("Supervisor");
    private final static String SUBORDINATE_COLUMN_TITLE = PropertyUtil.getSubordinateColumnTitle("Subordinate");
    
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
