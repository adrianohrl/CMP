package tech.adrianohrl.stile.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.production.Phase;
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
public class PhasesReader extends AbstractReader<Phase> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getPhaseColumnTitle("Name");
    private final static String SECTOR_COLUMN_TITLE = PropertyUtil.getPhaseColumnTitle("Sector");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SECTOR_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Phase build(List<Field> fields) throws IOException {
        String sectorName = Field.getFieldValue(fields, SECTOR_COLUMN_TITLE);
        Sector sector = getSector(sectorName);
        if (sector == null) {
            throw  new IOException(sectorName + " sector is not registered yet!!!");
        }
        return new Phase(Field.getFieldValue(fields, NAME_COLUMN_TITLE), sector);
    }
    
    protected Sector getSector(String sectorName) {
        return new Sector(sectorName, null);
    }
    
}
