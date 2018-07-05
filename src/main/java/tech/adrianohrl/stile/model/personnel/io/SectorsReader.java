package tech.adrianohrl.stile.model.personnel.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Sector;
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
public class SectorsReader extends AbstractReader<Sector> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getSectorColumnTitle("Name");
    private final static String SUPERVISOR_COLUMN_TITLE = PropertyUtil.getSectorColumnTitle("Supervisor");

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
