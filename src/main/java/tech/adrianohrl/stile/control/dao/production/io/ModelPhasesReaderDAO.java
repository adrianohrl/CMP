/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.production.ModelPhaseDAO;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.model.production.io.ModelPhasesReader;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelPhasesReaderDAO extends ReaderDAO<Model, ModelDAO> {
    
    private final ModelPhasesReader modelPhasesReader;

    public ModelPhasesReaderDAO(EntityManager em) {
        super(em, new ModelDAO(em));
        this.modelPhasesReader = new Reader();
        super.setReader(modelPhasesReader);
    }
    
    @Override
    protected void register() {
        ModelPhaseDAO modelPhaseDAO = new ModelPhaseDAO(em);
        for (Model model : modelPhasesReader) {
            if (dao.isRegistered(model)) {
                for (ModelPhase phase : model.getPhases()) {
                    modelPhaseDAO.create(phase);
                }
                dao.update(model);
                registeredEntities.add(model);
            }
        }
    }
    
    private class Reader extends ModelPhasesReader {
        
        @Override
        protected Model getModel(String reference) {
            ModelDAO modelDAO = new ModelDAO(em);
            return modelDAO.find(reference);
        }

        @Override
        protected Phase getPhase(String name) {
            PhaseDAO phaseDAO = new PhaseDAO(em);
            return phaseDAO.find(name);
        }

    }
    
}
