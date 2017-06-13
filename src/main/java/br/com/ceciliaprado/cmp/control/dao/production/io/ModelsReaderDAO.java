/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production.io;

import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ModelPhaseDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.control.model.production.io.ModelsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
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
