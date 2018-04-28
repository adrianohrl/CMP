/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.util.AbstractFilter;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public class FindByEmployee<T extends AbstractEmployeeRelatedEvent> extends AbstractFilter<T> {
    
    private final Employee employee;

    public FindByEmployee(Employee employee) {
        if (employee == null) {
            throw new RuntimeException("A non-null employee object is necessary for filtering!!!");
        }
        this.employee = employee;
    }

    @Override
    public void execute(T event) {
        if (employee.equals(event.getEmployee())) {
            super.add(event);
        }
    }
    
    @Override
    public EmployeeRelatedEventsList<T> getItems() {
        return new EmployeeRelatedEventsList(super.getItems());
    }
    
}
