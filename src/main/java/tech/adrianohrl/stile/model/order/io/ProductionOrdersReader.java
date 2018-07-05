package tech.adrianohrl.stile.model.order.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionOrdersReader extends AbstractReader<ProductionOrder> {
    
    /** Column Titles **/
    private final static String REFERENCE_COLUMN_TITLE = "Reference";
    private final static String MODEL_REFERENCE_COLUMN_TITLE = "Model Reference";

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(REFERENCE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(MODEL_REFERENCE_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected ProductionOrder build(List<Field> fields) throws IOException {
        String modelReference = Field.getFieldValue(fields, MODEL_REFERENCE_COLUMN_TITLE);
        Model model = getModel(modelReference);
        if (model == null) {
            throw  new IOException(modelReference + " model is not registered yet!!!");
        }
        return new ProductionOrder(Field.getFieldValue(fields, REFERENCE_COLUMN_TITLE), model);
    }
    
    protected Model getModel(String modelReference) {
        return new Model(modelReference, modelReference);
    }
    
}
