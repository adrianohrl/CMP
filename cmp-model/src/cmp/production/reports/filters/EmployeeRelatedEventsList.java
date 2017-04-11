/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports.filters;

import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.personal.Employee;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author adrianohrl
 */
public class EmployeeRelatedEventsList extends EventsList<AbstractEmployeeRelatedEvent> {

    public EmployeeRelatedEventsList() {
    }

    public EmployeeRelatedEventsList(Collection<? extends AbstractEmployeeRelatedEvent> c) {
        super(c);
    }
    
    public ArrayList<Employee> getInvolvedEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (AbstractEmployeeRelatedEvent event : this) {
            Employee employee = event.getEmployee();
            if (!employees.contains(employee)) {
                employees.add(employee);
            }
        }
        Collections.sort(employees);
        return employees;
    }
    
}
