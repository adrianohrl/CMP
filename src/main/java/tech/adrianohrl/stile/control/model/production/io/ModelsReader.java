package tech.adrianohrl.stile.control.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.DoubleField;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelsReader extends AbstractReader<Model> {
    
    /** Column Titles **/
    private final static String REFERENCE_COLUMN_TITLE = PropertyUtil.getModelColumnTitle("Reference");
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getModelColumnTitle("Name");
    private final static String FAMILY_COLUMN_TITLE = PropertyUtil.getModelColumnTitle("Family");
    private final static String COLLECTION_COLUMN_TITLE = PropertyUtil.getModelColumnTitle("Collection");
    private final static String CHART_COLUMN_TITLE = PropertyUtil.getModelColumnTitle("Chart");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(REFERENCE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(FAMILY_COLUMN_TITLE, true));
        defaultFields.add(new StringField(COLLECTION_COLUMN_TITLE, true));
        defaultFields.add(new StringField(CHART_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Model build(List<Field> fields) throws IOException {
        String reference = Field.getFieldValue(fields, REFERENCE_COLUMN_TITLE);
        String name = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        String familyName = Field.getFieldValue(fields, FAMILY_COLUMN_TITLE);
        Family family = getFamily(familyName);
        if (family == null) {
            throw new IOException(familyName + " family is not registered yet!!!");
        }
        String collectionName = Field.getFieldValue(fields, COLLECTION_COLUMN_TITLE);
        Collection collection = getCollection(collectionName);
        if (collection == null) {
           throw new IOException(collectionName + " collection is not registered yet!!!");
        }
        String chartName = Field.getFieldValue(fields, CHART_COLUMN_TITLE);
        Chart chart = getChart(chartName);
        if (chart == null) {
            throw new IOException(chartName + " chart is not registered yet!!!");
        }
        return new Model(reference, name, family, collection, chart);
    }
    
    protected Family getFamily(String name) {
        return new Family(name, null);
    }
    
    protected Collection getCollection(String name) {
        return new Collection(name);
    }
    
    protected Chart getChart(String name) {
        return new Chart(name, name);
    }
    
}
