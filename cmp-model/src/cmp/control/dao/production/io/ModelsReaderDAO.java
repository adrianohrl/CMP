/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production.io;

import cmp.control.dao.production.ModelDAO;
import cmp.control.dao.production.ModelPhaseDAO;
import cmp.control.dao.production.PhaseDAO;
import cmp.control.model.production.io.ModelsReader;
import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.ModelPhase;
import cmp.model.production.Phase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ModelsReaderDAO extends ModelsReader {
    
    private final EntityManager em;
    private final List<Model> registeredModels = new ArrayList<>();

    public ModelsReaderDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        ModelDAO modelDAO = new ModelDAO(em);
        ModelPhaseDAO phaseDAO = new ModelPhaseDAO(em);
        for (Model model : getReadEntities()) {
            if (!modelDAO.isRegistered(model)) {
                for (ModelPhase phase : model.getPhases()) {
                    phaseDAO.create(phase);
                }
                modelDAO.create(model);
                registeredModels.add(model);
            }
        }
    }

    @Override
    protected Phase getPhase(String phaseName) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        return phaseDAO.find(phaseName);
    }

    @Override
    public Iterator<Model> iterator() {
        return registeredModels.iterator();
    }
    
}
