/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production.io;

import cmp.control.dao.personnel.SectorDAO;
import cmp.control.dao.production.PhaseDAO;
import cmp.control.model.production.io.PhasesReader;
import cmp.exceptions.IOException;
import cmp.model.personnel.Sector;
import cmp.model.production.Phase;
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

    @Override
    public Iterator<Phase> iterator() {
        return registeredPhases.iterator();
    }
    
}
