package tech.adrianohrl.stile.model.events;

import tech.adrianohrl.stile.model.personnel.Employee;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class TimeClockEvent extends AbstractEmployeeRelatedEvent<Employee> {
    
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
