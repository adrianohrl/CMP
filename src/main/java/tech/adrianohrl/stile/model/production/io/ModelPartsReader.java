package tech.adrianohrl.stile.model.production.io;

import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Fabric;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPart;
import tech.adrianohrl.stile.util.PropertyUtil;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelPartsReader extends AbstractReader<Model> {
    
    /** Column Titles **/
    private final static String MODEL_COLUMN_TITLE = PropertyUtil.getModelPartColumnTitle("Model");
    private final static String PART_COLUMN_TITLE = PropertyUtil.getModelPartColumnTitle("Part");
    private final static String PROGRAM_COLUMN_TITLE = PropertyUtil.getModelPartColumnTitle("Program");
    private final static String OBSERVATION_COLUMN_TITLE = PropertyUtil.getModelPartColumnTitle("Observation");
    private final static String FABRIC_COLUMN_TITLE = PropertyUtil.getModelPartColumnTitle("Fabric");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(MODEL_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PART_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PROGRAM_COLUMN_TITLE, true));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        defaultFields.add(new StringField(FABRIC_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Model build(List<Field> fields) throws IOException {
        String modelReference = Field.getFieldValue(fields, MODEL_COLUMN_TITLE);
        String partName = Field.getFieldValue(fields, PART_COLUMN_TITLE);
        String program = Field.getFieldValue(fields, PROGRAM_COLUMN_TITLE);
        String observation = Field.getFieldValue(fields, OBSERVATION_COLUMN_TITLE);
        Fabric fabric = getFabric(Field.getFieldValue(fields, FABRIC_COLUMN_TITLE));
        if (fabric == null) {
            throw new IOException(fabric + " fabric is not registered yet!!!");
        }
        boolean containsModel = contains(modelReference);
        Model model = !containsModel ? getModel(modelReference) : get(modelReference);
        if (model == null) {
            throw new IOException(modelReference + " model is not registered yet!!!");
        }
        ModelPart modelPart = new ModelPart(partName, program, observation, fabric);
        if (model.getParts().contains(modelPart)) {
            return null;
        }
        model.getParts().add(modelPart);
        return !containsModel ? model : null;
    }
    
    protected Model getModel(String reference) {
        return new Model(reference, reference);
    }
    
    protected Fabric getFabric(String name) {
        return new Fabric(name, "", null);
    }
    
    protected boolean contains(String reference) {
        for (Model model : getReadEntities()) {
            if (reference.equalsIgnoreCase(model.getReference())) {
                return true;
            }
        }
        return false;
    }
    
    protected Model get(String reference) {
        for (Model model : getReadEntities()) {
            if (reference.equalsIgnoreCase(model.getReference())) {
                return model;
            }
        }
        return null;
    }
    
}
