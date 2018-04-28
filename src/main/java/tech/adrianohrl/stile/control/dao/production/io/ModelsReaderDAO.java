/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.production.ModelPhaseDAO;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.control.model.production.io.ModelsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;
import java.io.InputStream;
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
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
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

    public List<Model> getRegisteredModels() {
        return registeredModels;
    }

    @Override
    public Iterator<Model> iterator() {
        return registeredModels.iterator();
    }
    
}
