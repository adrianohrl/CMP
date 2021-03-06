package tech.adrianohrl.stile.model.personnel.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.personnel.Machine;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class MachinesReader extends AbstractReader<Sector> {
    
    /** Column Titles **/
    private final static String SECTOR_COLUMN_TITLE = PropertyUtil.getMachineColumnTitle("Sector");
    private final static String MACHINE_COLUMN_TITLE = PropertyUtil.getMachineColumnTitle("Machine");
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(SECTOR_COLUMN_TITLE, true));
        defaultFields.add(new StringField(MACHINE_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Sector build(List<Field> fields) throws IOException {
        String machineName = Field.getFieldValue(fields, MACHINE_COLUMN_TITLE);
        String sectorName = Field.getFieldValue(fields, SECTOR_COLUMN_TITLE);
        boolean containsSector = contains(sectorName);
        Sector sector = !containsSector ? getSector(sectorName) : get(sectorName);
        if (sector == null) {
            throw new IOException(sectorName + " sector is not registered yet!!!");
        }
        Machine machine = new Machine(machineName);
        if (sector.getMachines().contains(machine)) {
            return null;
        }
        sector.getMachines().add(machine);
        return !containsSector ? sector : null;
    }
    
    protected Sector getSector(String name) {
        return new Sector(name, new Supervisor());
    }
    
    protected boolean contains(String sectorName) {
        for (Sector sector : getReadEntities()) {
            if (sectorName.equalsIgnoreCase(sector.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected Sector get(String sectorName) {
        for (Sector sector : getReadEntities()) {
            if (sectorName.equalsIgnoreCase(sector.getName())) {
                return sector;
            }
        }
        return null;
    }
    
}
