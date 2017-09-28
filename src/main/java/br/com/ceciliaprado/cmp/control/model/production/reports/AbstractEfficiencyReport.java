/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByType;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.Calendar;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractEfficiencyReport extends AbstractAttendanceReport {
    
    protected final EmployeeRelatedEventsList<EntryEvent> entryEvents = new EmployeeRelatedEventsList<>();
    
    public AbstractEfficiencyReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        FindByType<EntryEvent> filter = new FindByType<>(EntryEvent.class);
        events.execute(filter);
        for (AbstractEmployeeRelatedEvent event : filter) {
            this.entryEvents.add((EntryEvent) event);
        }
    }

    public EmployeeRelatedEventsList<EntryEvent> getEntryEvents() {
        return entryEvents;
    }

    @Override
    protected TreeMap<String, ReportNumericSeries> getSeriesMap(Employee employee) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
