/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel.io;

import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.model.personnel.io.SupervisorsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
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
public class SupervisorsReaderDAO extends SupervisorsReader {
    
    private final EntityManager em;
    private final List<Manager> registeredManagers = new ArrayList<>();

    public SupervisorsReaderDAO(EntityManager em) {
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
        ManagerDAO managerDAO = new ManagerDAO(em);
        for (Manager manager : getReadEntities()) {
            managerDAO.update(manager);
            registeredManagers.add(manager);
        }
    }

    @Override
    protected Supervisor getSupervisor(String supervisorName) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.find(supervisorName);
    }

    @Override
    protected Manager getManager(String managerName) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        return managerDAO.find(managerName);
    }

    public List<Manager> getRegisteredManagers() {
        return registeredManagers;
    }
    
    @Override
    public Iterator<Manager> iterator() {
        return registeredManagers.iterator();
    }
    
}
