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
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractAttendanceReport extends AbstractProductionReport {
    
    protected final EmployeeRelatedEventsList<TimeClockEvent> timeClockEvents;
    protected final EventsPeriodBuilder builder;
    
    protected AbstractAttendanceReport(List<TimeClockEvent> timeClockEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(manager, startDate, endDate);
        this.timeClockEvents = new EmployeeRelatedEventsList<>();
        for (TimeClockEvent timeClockEvent : timeClockEvents) {
            Calendar date = timeClockEvent.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The time clock event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The time clock event date must not be after the report end date!!!");
            }
            this.timeClockEvents.add((TimeClockEvent) timeClockEvent);
        }
        builder = new EventsPeriodBuilder(new EmployeeRelatedEventsList<>(this.timeClockEvents));
    }
    
    protected AbstractAttendanceReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(manager, startDate, endDate);
        timeClockEvents = new EmployeeRelatedEventsList<>();
        for (AbstractEmployeeRelatedEvent event : events) {
            if (event instanceof TimeClockEvent) {
                Calendar date = event.getEventDate();
                if (date.before(startDate)) {
                    throw new ReportException("The time clock event date must not be before the report start date!!!");
                }
                if (date.after(endDate)) {
                    throw new ReportException("The time clock event date must not be after the report end date!!!");
                }
                timeClockEvents.add((TimeClockEvent) event);
            }
        }
        builder = new EventsPeriodBuilder(new EmployeeRelatedEventsList<>(timeClockEvents));
    }
    
    protected double getTotal(Employee employee, Function<EmployeeEventsPeriodBuilder, Double> function) {
        return function.apply(builder.get(employee));
    }
    
    protected TreeMap<Calendar, Double> getSeries(Employee employee, Function<EmployeeEventsPeriodBuilder, Double> function) throws ReportException {
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
            double total = 0.0;
            if (!filter.getItems().isEmpty()) {
                EventsPeriodBuilder b = new EventsPeriodBuilder(filter.getItems());
                EmployeeEventsPeriodBuilder employeeBuilder = b.get(employee);
                if (employeeBuilder != null) {
                    total += function.apply(employeeBuilder);
                }
            }
            map.put((Calendar) dayStart.clone(), total);
            dayStart.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }   

    public List<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }
    
}
