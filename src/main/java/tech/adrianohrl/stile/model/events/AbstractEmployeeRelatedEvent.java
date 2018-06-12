package tech.adrianohrl.stile.model.events;

import tech.adrianohrl.stile.model.personnel.Employee;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEmployeeRelatedEvent<T extends Employee> extends AbstractEvent {
    
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
