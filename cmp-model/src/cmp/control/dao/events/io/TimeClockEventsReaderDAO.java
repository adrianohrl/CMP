/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.events.io;

import cmp.control.dao.personnel.EmployeeDAO;
import cmp.control.model.events.io.TimeClockEventsReader;
import cmp.exceptions.IOException;
import cmp.model.personnel.Employee;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderDAO extends TimeClockEventsReader {
    
    private final EntityManager em;

    public TimeClockEventsReaderDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    protected Employee createEmployee(String employeeName) throws IOException {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        Employee employee = (Employee) employeeDAO.find(employeeName);
        if (employee == null) {
            throw new IOException("The input employee (whose name is " + employeeName + ") is not registered yet!!!");
        }
        return employee;
    }
    
}
