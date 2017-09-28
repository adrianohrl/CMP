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
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.Calendar;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractAttendanceReport extends AbstractProductionReport {
    
    protected final EmployeeRelatedEventsList<TimeClockEvent> timeClockEvents = new EmployeeRelatedEventsList<>();
    
    protected AbstractAttendanceReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        FindByType<TimeClockEvent> filter = new FindByType<>(TimeClockEvent.class);
        this.builder.execute(filter);
        for (AbstractEmployeeRelatedEvent event : filter) {
            this.timeClockEvents.add((TimeClockEvent) event);
        }
    }

    public EmployeeRelatedEventsList<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }

    protected TreeMap<String, ReportNumericSeries> getSeriesMap(Employee employee) {
        TreeMap<String, ReportNumericSeries> map = new TreeMap<>();
        ReportNumericSeries series;
        series = new ReportDoubleSeries(0, "Total Duration", employee, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, 1 / 60.0);
        map.put(series.getName(), series);
        return map;
    }
    
}
