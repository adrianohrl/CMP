/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.production.io;

import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.ProductionOrder;
import cmp.util.AbstractReader;
import cmp.util.Field;
import cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
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
