/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personnel.io;

import cmp.control.dao.personnel.ManagerDAO;
import cmp.control.dao.personnel.SupervisorDAO;
import cmp.control.model.personnel.io.SupervisorsReader;
import cmp.exceptions.IOException;
import cmp.model.personnel.Manager;
import cmp.model.personnel.Supervisor;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SupervisorsReaderDAO extends SupervisorsReader {
    
    private final EntityManager em;

    public SupervisorsReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        ManagerDAO managerDAO = new ManagerDAO(em);
        for (Manager manager : this) {
            managerDAO.update(manager);
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
    
}
