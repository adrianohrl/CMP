/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel.io;

import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.model.personnel.io.SectorsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SectorsReaderDAO extends SectorsReader {
    
    private final EntityManager em;
    private final List<Sector> registeredSectors = new ArrayList<>();

    public SectorsReaderDAO(EntityManager em) {
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
        SectorDAO sectorDAO = new SectorDAO(em);
        for (Sector sector : getReadEntities()) {
            if (!sectorDAO.isRegistered(sector)) {
                sectorDAO.create(sector);
                registeredSectors.add(sector);
            }
        }
    }
    
    @Override
    protected Supervisor getSupervisor(String supervisorName) throws IOException {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.find(supervisorName);
    }

    public List<Sector> getRegisteredSectors() {
        return registeredSectors;
    }

    @Override
    public Iterator<Sector> iterator() {
        return registeredSectors.iterator();
    }
    
}
