/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production.io;

import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.control.model.production.io.PhasesReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PhasesReaderDAO extends PhasesReader {
    
    private final EntityManager em;
    private final List<Phase> registeredPhases = new ArrayList<>();

    public PhasesReaderDAO(EntityManager em) {
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
        PhaseDAO phaseDAO = new PhaseDAO(em);
        for (Phase phase : getReadEntities()) {
            if (!phaseDAO.isRegistered(phase)) {
                phaseDAO.create(phase);
                registeredPhases.add(phase);
            }
        }
    }

    @Override
    protected Sector getSector(String sectorName) {
        SectorDAO sectorDAO = new SectorDAO(em);
        return sectorDAO.find(sectorName);
    }

    public List<Phase> getRegisteredPhases() {
        return registeredPhases;
    }

    @Override
    public Iterator<Phase> iterator() {
        return registeredPhases.iterator();
    }
    
}
