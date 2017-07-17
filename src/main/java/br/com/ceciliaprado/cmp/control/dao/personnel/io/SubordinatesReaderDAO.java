/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel.io;

import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.model.personnel.io.SubordinatesReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
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
public class SubordinatesReaderDAO extends SubordinatesReader {
    
    private final EntityManager em;
    private final List<Supervisor> registeredSupervisors = new ArrayList<>();

    public SubordinatesReaderDAO(EntityManager em) {
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
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        for (Supervisor supervisor : getReadEntities()) {
            supervisorDAO.update(supervisor);
            registeredSupervisors.add(supervisor);
        }
    }

    @Override
    protected Subordinate getSubordinate(String subordinateName) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        return subordinateDAO.find(subordinateName);
    }

    @Override
    protected Supervisor getSupervisor(String supervisorName) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.find(supervisorName);
    }

    public List<Supervisor> getRegisteredSupervisors() {
        return registeredSupervisors;
    }

    @Override
    public Iterator<Supervisor> iterator() {
        return registeredSupervisors.iterator();
    }
    
}
