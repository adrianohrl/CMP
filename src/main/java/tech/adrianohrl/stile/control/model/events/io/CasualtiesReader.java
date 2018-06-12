package tech.adrianohrl.stile.control.model.events.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.BooleanField;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
