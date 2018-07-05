package tech.adrianohrl.stile.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FamiliesReader extends AbstractReader<Family> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getFamilyColumnTitle("Name");
    private final static String COLLECTION_COLUMN_TITLE = PropertyUtil.getFamilyColumnTitle("Collection");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(COLLECTION_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Family build(List<Field> fields) throws IOException {
        String familyName = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        String collectionName = Field.getFieldValue(fields, COLLECTION_COLUMN_TITLE);
        Collection collection = getCollection(collectionName);
        if (collection == null) {
            throw new IOException(collectionName + " collection is not registered yet!!!");
        }
        return new Family(familyName, collection);
    }
    
    protected Collection getCollection(String name) {
        return new Collection(name);
    }
    
}
