/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events.io;

import cmp.dao.personal.EmployeeDAO;
import cmp.events.io.TimeClockEventsReader;
import cmp.exceptions.IOException;
import cmp.model.personal.Employee;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderDAO extends TimeClockEventsReader {
    
    private EntityManager em;

    public TimeClockEventsReaderDAO(EntityManager em, String fileName) throws IOException {
        super(fileName);
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
