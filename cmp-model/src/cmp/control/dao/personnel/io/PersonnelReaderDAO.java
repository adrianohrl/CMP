/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personnel.io;

import cmp.control.dao.personnel.EmployeeDAO;
import cmp.control.dao.personnel.ManagerDAO;
import cmp.control.dao.personnel.SubordinateDAO;
import cmp.control.dao.personnel.SupervisorDAO;
import cmp.control.model.personnel.io.PersonnelReader;
import cmp.exceptions.IOException;
import cmp.model.personnel.Employee;
import cmp.model.personnel.Manager;
import cmp.model.personnel.Subordinate;
import cmp.model.personnel.Supervisor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonnelReaderDAO extends PersonnelReader {
    
    private final EntityManager em;
    private final List<Employee> registeredEmployees = new ArrayList<>();

    public PersonnelReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        for (Employee employee : getReadEntities()) {
            if (!employeeDAO.isRegistered(employee)) {
                if (employee instanceof Subordinate) {
                    register((Subordinate) employee);
                } else if (employee instanceof Supervisor) {
                    register((Supervisor) employee);
                } else {
                    register((Manager) employee);
                }
                registeredEmployees.add(employee);
            }
        }
    }
    
    private void register(Subordinate subordinate) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinateDAO.create(subordinate);
    }
    
    private void register(Supervisor supervisor) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisorDAO.create(supervisor);
    }
    
    private void register(Manager manager) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        managerDAO.create(manager);
    }

    @Override
    public Iterator<Employee> iterator() {
        return registeredEmployees.iterator();
    }
    
}
