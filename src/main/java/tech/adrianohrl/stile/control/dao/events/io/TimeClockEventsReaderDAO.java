/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events.io;

import tech.adrianohrl.stile.control.dao.events.TimeClockEventDAO;
import tech.adrianohrl.stile.control.dao.personnel.EmployeeDAO;
import tech.adrianohrl.stile.control.model.events.io.TimeClockEventsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import java.io.InputStream;
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
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
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

    public List<TimeClockEvent> getRegisteredEvents() {
        return registeredEvents;
    }

    @Override
    public Iterator<TimeClockEvent> iterator() {
        return registeredEvents.iterator();
    }
    
}
