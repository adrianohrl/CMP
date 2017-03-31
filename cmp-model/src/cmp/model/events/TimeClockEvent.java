/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personal.Employee;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEvent extends AbstractEvent {
    
    private Employee employee;
    private boolean arrival;

    public TimeClockEvent() {
    }

    public TimeClockEvent(Employee employee, boolean arrival, Calendar eventDate, String observation) {
        super(eventDate, observation);
        this.employee = employee;
        this.arrival = arrival;
    }

    @Override
    public boolean equals(AbstractEvent event) {
        return event instanceof TimeClockEvent && equals((TimeClockEvent) event);
    }
    
    public boolean equals(TimeClockEvent event) {
        return super.equals(event) && employee.equals(event.employee);
    }

    @Override
    public String toString() {
        return employee + " " + (arrival ? "ARRIVED" : "DEPARTURED") + " on " + super.toString();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isArrival() {
        return arrival;
    }

    public void setArrival(boolean arrival) {
        this.arrival = arrival;
    }
    
}
