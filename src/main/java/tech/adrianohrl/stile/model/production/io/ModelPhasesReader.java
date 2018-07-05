/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.production.io;

import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.util.PropertyUtil;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.DoubleField;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelPhasesReader extends AbstractReader<Model> {
    
    /** Column Titles **/
    private final static String REFERENCE_COLUMN_TITLE = PropertyUtil.getModelPhaseColumnTitle("Model");
    private final static String PHASE_COLUMN_TITLE = PropertyUtil.getModelPhaseColumnTitle("Phase");
    private final static String EXPECTED_DURATION_COLUMN_TITLE = PropertyUtil.getModelPhaseColumnTitle("Duration");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(REFERENCE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PHASE_COLUMN_TITLE, true));
        defaultFields.add(new DoubleField(EXPECTED_DURATION_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Model build(List<Field> fields) throws IOException {
        String modelReference = Field.getFieldValue(fields, REFERENCE_COLUMN_TITLE);
        boolean containsModel = contains(modelReference);
        String phaseName = Field.getFieldValue(fields, PHASE_COLUMN_TITLE);
        Phase phase = getPhase(phaseName);
        if (phase == null) {
            throw new IOException(phaseName + " phase is not registered yet!!!");
        }
        ModelPhase modelPhase = new ModelPhase(phase, Field.getFieldValue(fields, EXPECTED_DURATION_COLUMN_TITLE));
        Model model = !containsModel ? getModel(modelReference) : get(modelReference);
        if (model == null) {
            throw new IOException(modelReference + " model is not registered yet!!!");
        }
        if (model.getPhases().contains(modelPhase)) {
            return null;
        }
        model.getPhases().add(modelPhase);
        return !containsModel ? model : null;
    }
    
    protected Model getModel(String reference) {
        return new Model(reference, reference);
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
