/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personal.Employee;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author adrianohrl
 */
@Entity
public class TimeClockEvent extends AbstractEmployeeRelatedEvent {
    
    private boolean arrival;

    public TimeClockEvent() {
    }

    public TimeClockEvent(Employee employee, boolean arrival, Calendar eventDate, String observation) {
        super(employee, eventDate, observation);
        this.arrival = arrival;
    }

    @Override
    public boolean equals(AbstractEmployeeRelatedEvent event) {
        return event instanceof TimeClockEvent && super.equals(event);
    }

    @Override
    public String toString() {
        return super.toString() + getEmployee() + " " + (arrival ? "ARRIVED" : "DEPARTURED");
    }

    public boolean isArrival() {
        return arrival;
    }

    public void setArrival(boolean arrival) {
        this.arrival = arrival;
    }
    
}
