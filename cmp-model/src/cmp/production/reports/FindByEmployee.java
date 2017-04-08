/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.personal.Employee;
import cmp.util.AbstractFilter;

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
    
}
