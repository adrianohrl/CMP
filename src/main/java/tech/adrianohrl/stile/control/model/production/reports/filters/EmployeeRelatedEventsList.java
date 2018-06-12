package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public class EmployeeRelatedEventsList<T extends AbstractEmployeeRelatedEvent> extends EventsList<T> {

    public EmployeeRelatedEventsList() {
    }

    public EmployeeRelatedEventsList(Collection<? extends T> c) {
        super(c);
    }
    
    public List<Employee> getInvolvedEmployees() {
        List<Employee> employees = new ArrayList<>();
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
