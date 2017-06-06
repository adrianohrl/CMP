/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.production.io;

import cmp.exceptions.IOException;
import cmp.exceptions.ProductionException;
import cmp.model.production.Model;
import cmp.model.production.ModelPhase;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import cmp.util.AbstractReader;
import cmp.util.Field;
import cmp.util.IntegerField;
import cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class PhaseProductionOrdersReader extends AbstractReader<PhaseProductionOrder> {
    
    /** Column Titles **/
    private final static String PRODUCTION_ORDER_COLUMN_TITLE = "Production Order";
    private final static String PHASE_COLUMN_TITLE = "Phase";
    private final static String TOTAL_QUANTITY_COLUMN_TITLE = "Total Quantity";

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(PRODUCTION_ORDER_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PHASE_COLUMN_TITLE, true));
        defaultFields.add(new IntegerField(TOTAL_QUANTITY_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected PhaseProductionOrder build(List<Field> fields) throws IOException {
        String orderReference = Field.getFieldValue(fields, PRODUCTION_ORDER_COLUMN_TITLE);
        ProductionOrder productionOrder = getProductionOrder(orderReference);
        if (productionOrder == null) {
            throw  new IOException(orderReference + " production order is not registered yet!!!");
        }
        String phaseName = Field.getFieldValue(fields, PHASE_COLUMN_TITLE);
        ModelPhase phase = getPhase(phaseName, productionOrder.getModel());
        if (phase == null) {
            throw new IOException(phaseName + " model phase is not registered yet!!!");
        }
        int totalQuantity = Field.getFieldValue(fields, TOTAL_QUANTITY_COLUMN_TITLE);
        if (totalQuantity <= 0) {
            throw new IOException("The total quantity of the " + phaseName + " phase of " + productionOrder + " must be posistive!!!");
        }
        try {
            return new PhaseProductionOrder(phase, productionOrder, totalQuantity);
        } catch (ProductionException e) {
            throw new IOException("ProductionException catched: " + e.getMessage());
        }
    }
    
    protected ProductionOrder getProductionOrder(String orderReference) {
        return new ProductionOrder(orderReference, new Model());
    }
    
    protected ModelPhase getPhase(String phaseName, Model model) {
        return model != null ? model.getPhase(new Phase(phaseName, null)) : null;
    }
    
}
