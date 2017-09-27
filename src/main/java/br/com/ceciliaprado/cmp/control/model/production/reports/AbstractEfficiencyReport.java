/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByPeriod;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractEfficiencyReport extends AbstractProductionReport {
    
    protected final List<TimeClockEvent> timeClockEvents;
    protected final List<EntryEvent> entryEvents;
    protected final EventsPeriodBuilder builder;
    
    public AbstractEfficiencyReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(manager, startDate, endDate);
        timeClockEvents = new ArrayList<>();
        entryEvents = new ArrayList<>();
        for (AbstractEmployeeRelatedEvent event : events) {
            Calendar date = event.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The event date must not be after the report end date!!!");
            }
            if (event instanceof TimeClockEvent) {
                timeClockEvents.add((TimeClockEvent) event);
            } else if (event instanceof EntryEvent) {
                entryEvents.add((EntryEvent) event);
            }
        }
        builder = new EventsPeriodBuilder(events);
    }

    public AbstractEfficiencyReport(List<TimeClockEvent> timeClockEvents, List<EntryEvent> entryEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(manager, startDate, endDate);
        EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
        for (TimeClockEvent event : timeClockEvents) {
            Calendar date = event.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The time clock event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The time clock event date must not be after the report end date!!!");
            }
        }
        this.timeClockEvents = timeClockEvents;
        events.addAll(timeClockEvents);
        for (EntryEvent event : entryEvents) {
            Calendar date = event.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The entry event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The entry event date must not be after the report end date!!!");
            }
        }
        this.entryEvents = entryEvents;
        events.addAll(entryEvents);
        builder = new EventsPeriodBuilder(events);
    }
    
    protected TreeMap<Calendar, Double> getDailyTotalDuration(Employee employee) throws ReportException {
        TreeMap<Calendar, Double> map = new TreeMap<>();
        Calendar dayStart = (Calendar) startDate.clone();
        dayStart.set(Calendar.HOUR_OF_DAY, 0);
        dayStart.set(Calendar.MINUTE, 0);
        dayStart.set(Calendar.SECOND, 0);
        dayStart.set(Calendar.MILLISECOND, 0);
        Calendar dayEnd = (Calendar) dayStart.clone();
        dayEnd.add(Calendar.DAY_OF_MONTH, 1);
        dayEnd.add(Calendar.MILLISECOND, -1);
        while (!dayEnd.after(endDate)) {
            FindByPeriod<AbstractEmployeeRelatedEvent> filter = new FindByPeriod<>(dayStart, dayEnd);
            builder.execute(filter);
            double totalDuration = 0.0;
            if (!filter.getItems().isEmpty()) {
                EventsPeriodBuilder b = new EventsPeriodBuilder(filter.getItems());
                EmployeeEventsPeriodBuilder employeeBuilder = b.get(employee);
                if (employeeBuilder != null) {
                    totalDuration = employeeBuilder.getTotalDuration();
                }
            }
            map.put((Calendar) dayStart.clone(), totalDuration);
            dayStart.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }   

    public List<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }

    public List<EntryEvent> getEntryEvents() {
        return entryEvents;
    }
    
}
