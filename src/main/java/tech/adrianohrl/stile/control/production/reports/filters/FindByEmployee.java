package tech.adrianohrl.stile.control.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
