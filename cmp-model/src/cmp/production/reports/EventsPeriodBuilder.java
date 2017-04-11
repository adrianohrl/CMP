/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.production.reports.filters.FindByEmployee;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.exceptions.ReportException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.personal.Employee;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class EventsPeriodBuilder implements Iterable<EmployeeEventsPeriodBuilder> {
    
    private final ArrayList<EmployeeEventsPeriodBuilder> builders = new ArrayList<>();
    
    public EventsPeriodBuilder(EmployeeRelatedEventsList events) throws ReportException {
        if (events.isEmpty()) {
            throw new ReportException("There is no event to build!!!");
        }
        build(events);
    }
    
    public EventsPeriodBuilder(ArrayList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        this(new EmployeeRelatedEventsList(events));
    }
    
    private void build(EmployeeRelatedEventsList events) throws ReportException {
        FindByEmployee filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            builders.add(new EmployeeEventsPeriodBuilder(employee, filter.getItems()));
        }
    }

    @Override
    public Iterator<EmployeeEventsPeriodBuilder> iterator() {
        return builders.iterator();
    }
    
}