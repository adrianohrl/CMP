/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.production.io;

import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.ModelPhase;
import cmp.model.production.Phase;
import cmp.util.AbstractReader;
import cmp.util.DoubleField;
import cmp.util.Field;
import cmp.util.StringField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class ModelsReader extends AbstractReader<Model> {
    
    /** Column Titles **/
    private final static String REFERENCE_COLUMN_TITLE = "Reference";
    private final static String NAME_COLUMN_TITLE = "Name";
    private final static String PHASE_COLUMN_TITLE = "Phase";
    private final static String EXPECTED_DURATION_COLUMN_TITLE = "Expected Duration";

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(REFERENCE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PHASE_COLUMN_TITLE, true));
        defaultFields.add(new DoubleField(EXPECTED_DURATION_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Model build(List<Field> fields) throws IOException {
        String reference = Field.getFieldValue(fields, REFERENCE_COLUMN_TITLE);
        String modelName = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        boolean containsModel = contains(reference);
        String phaseName = Field.getFieldValue(fields, PHASE_COLUMN_TITLE);
        Phase phase = getPhase(phaseName);
        if (phase == null) {
            throw new IOException(phaseName + " phase is not registered yet!!!");
        }
        ModelPhase modelPhase = new ModelPhase(phase, Field.getFieldValue(fields, EXPECTED_DURATION_COLUMN_TITLE));
        Model model = !containsModel ? new Model(reference, modelName) : get(reference);
        if (model.getPhases().contains(modelPhase)) {
            return null;
        }
        model.getPhases().add(modelPhase);
        return !containsModel ? model : null;
    }
    
    protected Phase getPhase(String phaseName) {
        return new Phase(phaseName, null);
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
