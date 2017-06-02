/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personnel.Employee;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractEmployeeRelatedEvent<T extends Employee> extends AbstractEvent {
    
    @ManyToOne(targetEntity = Employee.class, optional = false)
    private T employee;

    public AbstractEmployeeRelatedEvent() {
    }

    public AbstractEmployeeRelatedEvent(T employee, Calendar eventDate, String observation) {
        super(eventDate, observation);
        this.employee = employee;
    }

    @Override
    public boolean equals(AbstractEvent event) {
        return event instanceof AbstractEvent && equals((AbstractEmployeeRelatedEvent) event);
    }
    
    public boolean equals(AbstractEmployeeRelatedEvent event) {
        return super.equals(event) && employee.equals(event.employee);
    }

    public T getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }
    
}
