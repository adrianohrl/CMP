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
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 * @param <S>
 */
public abstract class AbstractAttendanceReport<S extends SeriesType> extends AbstractProductionReport<S> {
    
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

    protected Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> getSeriesMap(Employee employee) {
        Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> map = new TreeMap<>();
        ReportNumericSeries<AttendanceSeriesTypes> series = new ReportDoubleSeries<>(AttendanceSeriesTypes.TOTAL_QUANTITY, employee, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, 1 / 60.0);
        map.put(AttendanceSeriesTypes.TOTAL_QUANTITY, series);
        return map;
    }
    
}
