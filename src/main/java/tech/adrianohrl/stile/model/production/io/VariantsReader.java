package tech.adrianohrl.stile.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.production.Variant;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class VariantsReader extends AbstractReader<Variant> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getVariantColumnTitle("Name");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Variant build(List<Field> fields) throws IOException {
        return new Variant(Field.getFieldValue(fields, NAME_COLUMN_TITLE));
    }
    
}
