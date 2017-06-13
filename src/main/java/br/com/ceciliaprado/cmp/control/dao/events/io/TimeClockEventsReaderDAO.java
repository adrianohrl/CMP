/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events.io;

import br.com.ceciliaprado.cmp.control.dao.events.TimeClockEventDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.EmployeeDAO;
import br.com.ceciliaprado.cmp.control.model.events.io.TimeClockEventsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderDAO extends TimeClockEventsReader {
    
    private final EntityManager em;
    private final List<TimeClockEvent> registeredEvents = new ArrayList<>();

    public TimeClockEventsReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        TimeClockEventDAO eventDAO = new TimeClockEventDAO(em);
        for (TimeClockEvent event : getReadEntities()) {
            if (!eventDAO.isRegistered(event)) {
                eventDAO.create(event);
                registeredEvents.add(event);
            }
        }
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

    @Override
    public Iterator<TimeClockEvent> iterator() {
        return registeredEvents.iterator();
    }
    
}
