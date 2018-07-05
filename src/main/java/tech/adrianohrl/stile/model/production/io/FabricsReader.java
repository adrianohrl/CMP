package tech.adrianohrl.stile.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Fabric;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FabricsReader extends AbstractReader<Fabric> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getFabricColumnTitle("Name");
    private final static String OBSERVATION_COLUMN_TITLE = PropertyUtil.getFabricColumnTitle("Observation");
    private final static String COLLECTION_COLUMN_TITLE = PropertyUtil.getFabricColumnTitle("Collection");
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        defaultFields.add(new StringField(COLLECTION_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Fabric build(List<Field> fields) throws IOException {
        String name = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        String observation = Field.getFieldValue(fields, OBSERVATION_COLUMN_TITLE);
        String collectionName = Field.getFieldValue(fields, COLLECTION_COLUMN_TITLE);
        Collection collection = getCollection(collectionName);
        return new Fabric(name, observation, collection);
    }
    
    protected Collection getCollection(String name) {
        return new Collection(name);
    }
    
}
