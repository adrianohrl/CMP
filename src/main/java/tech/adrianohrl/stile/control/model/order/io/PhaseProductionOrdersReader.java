package tech.adrianohrl.stile.control.model.order.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.exceptions.ProductionException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.IntegerField;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
            throw new IOException("ProductionException caught: " + e.getMessage());
        }
    }
    
    protected ProductionOrder getProductionOrder(String orderReference) {
        return new ProductionOrder(orderReference, new Model());
    }
    
    protected ModelPhase getPhase(String phaseName, Model model) {
        return model != null ? model.getPhase(new Phase(phaseName, null)) : null;
    }
    
}
