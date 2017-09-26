/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByEmployee;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.util.CalendarFormat;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class EventsPeriodBuilder implements Iterable<EmployeeEventsPeriodBuilder> {
    
    private final EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events = new EmployeeRelatedEventsList<>();
    private final List<EmployeeEventsPeriodBuilder> builders = new ArrayList<>();
    
    public EventsPeriodBuilder(EmployeeRelatedEventsList events) throws ReportException {
        if (events.isEmpty()) {
            throw new ReportException("There is no event to build!!!");
        }
        this.events.addAll(events);
        prepare();
        build();
    }

    /*private void prepare() {
        Collections.sort(events);
        System.out.println("\n\nBefore preparation: (size: " + events.size() + ")");
        for (AbstractEmployeeRelatedEvent e : events) {
            System.out.println("\t" + e);
        }
        for (Employee employee : events.getInvolvedEmployees()) {
            FindByEmployee filter = new FindByEmployee(employee);
            events.execute(filter);
            EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> employeeEvents = filter.getItems();
            
            
            System.out.println("\n" + employee + "'s events before:");
            for (AbstractEmployeeRelatedEvent employeeEvent : employeeEvents) {
                System.out.println("\t" + employeeEvent);
            }
            
            int i = 0;
            AbstractEmployeeRelatedEvent event = employeeEvents.get(i);
            Calendar previousDate = event.getEventDate();
            
            Calendar dayStart = (Calendar) previousDate.clone();
            dayStart.set(Calendar.HOUR_OF_DAY, 0);
            dayStart.set(Calendar.MINUTE, 0);
            dayStart.set(Calendar.SECOND, 0);
            dayStart.set(Calendar.MILLISECOND, 0);
            Calendar dayEnd = (Calendar) dayStart.clone();
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.MILLISECOND, -1);
            System.out.println("DAY start: " + CalendarFormat.format(dayStart) + ", end: " + CalendarFormat.format(dayEnd));
            
            System.out.println("\n\nPreparing ...");
            
            if (!(event instanceof TimeClockEvent) || !((TimeClockEvent) event).isArrival()) {  
                event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                System.out.println("\tadding new arrival time clock event: " + event);
                events.add(event);
                employeeEvents.add(i++, event);
            }
            
            while (++i < employeeEvents.size())  {
                event = employeeEvents.get(i);
                Calendar nextDate = event.getEventDate();
                System.out.println("previous: " + (previousDate != null ? CalendarFormat.format(previousDate) : "null") + ", next: " + (nextDate != null ? CalendarFormat.format(nextDate) : "null"));
                if (Calendars.changedDay(previousDate, nextDate)) {
                    System.out.println("Changed day ...");
                    event = employeeEvents.get(i - 1);
                    System.out.println("\tprevious event: " + event);
                    if (!(event instanceof TimeClockEvent) || ((TimeClockEvent) event).isArrival()) { // yesterday
                        event = new TimeClockEvent(employee, false, (Calendar) dayEnd.clone(), null);
                        System.out.println("\tadding new departure time clock event: " + event);
                        events.add(event);
                        employeeEvents.add(i++, event);
                        nextDate = event.getEventDate();
                    }
                    while (dayStart.before(nextDate) && (dayEnd.before(nextDate) || dayEnd.equals(nextDate))) {
                        dayStart.add(Calendar.DAY_OF_MONTH, 1);
                        dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    System.out.println("DAY start: " + CalendarFormat.format(dayStart) + ", end: " + CalendarFormat.format(dayEnd));
                    event = employeeEvents.get(i);
                    System.out.println("\tnext event: " + event);
                    if (!(event instanceof TimeClockEvent) || !((TimeClockEvent) event).isArrival()) {  // today
                        event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                        System.out.println("\tadding new arrival time clock event: " + event);
                        events.add(event);
                        employeeEvents.add(i++, event);
                        event = employeeEvents.get(i);
                        nextDate = event.getEventDate();
                    }
                }
                previousDate = nextDate;           
            }
            System.out.println("\n" + employee + "'s events after:");
            for (AbstractEmployeeRelatedEvent employeeEvent : employeeEvents) {
                System.out.println("\t" + employeeEvent);
            }
        }
        Collections.sort(events);
        System.out.println("After preparation: (size: " + events.size() + ")");
        for (AbstractEmployeeRelatedEvent e : events) {
            System.out.println("\t" + e);
        }
    }*/
    
    private void prepare() {
        Collections.sort(events);
        System.out.println("\n\nBefore preparation: (size: " + events.size() + ")");
        for (AbstractEmployeeRelatedEvent e : events) {
            System.out.println("\t" + e);
        }
        for (Employee employee : events.getInvolvedEmployees()) {
            FindByEmployee filter = new FindByEmployee(employee);
            events.execute(filter);
            EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> employeeEvents = filter.getItems();
            
            
            System.out.println("\n" + employee + "'s events before:");
            for (AbstractEmployeeRelatedEvent employeeEvent : employeeEvents) {
                System.out.println("\t" + employeeEvent);
            }
            
            int i = 0;
            AbstractEmployeeRelatedEvent event = employeeEvents.get(i);
            Calendar previousDate = event.getEventDate();
            
            Calendar dayStart = (Calendar) previousDate.clone();
            dayStart.set(Calendar.HOUR_OF_DAY, 0);
            dayStart.set(Calendar.MINUTE, 0);
            dayStart.set(Calendar.SECOND, 0);
            dayStart.set(Calendar.MILLISECOND, 0);
            Calendar dayEnd = (Calendar) dayStart.clone();
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.MILLISECOND, -1);
            System.out.println("DAY start: " + CalendarFormat.format(dayStart) + ", end: " + CalendarFormat.format(dayEnd));
            
            System.out.println("\n\nPreparing ...");
            
            if (!(event instanceof TimeClockEvent) || !((TimeClockEvent) event).isArrival()) {  
                event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                System.out.println("\t(case 1) adding new ARRIVAL time clock event: " + event);
                events.add(event);
            }
            
            while (++i < employeeEvents.size())  {
                event = employeeEvents.get(i);
                Calendar nextDate = event.getEventDate();
                System.out.println("previous: " + CalendarFormat.format(previousDate) + ", next: " + CalendarFormat.format(nextDate));
                if (Calendars.changedDay(previousDate, nextDate)) {
                    System.out.println("Changed day ...");
                    event = employeeEvents.get(i - 1);
                    System.out.println("\tprevious event: " + event);
                    if (!(event instanceof TimeClockEvent) || ((TimeClockEvent) event).isArrival()) {
                        while (dayStart.after(previousDate) || dayEnd.before(previousDate)) {
                            dayStart.add(Calendar.DAY_OF_MONTH, 1);
                            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        System.out.println("DAY start: " + CalendarFormat.format(dayStart) + ", end: " + CalendarFormat.format(dayEnd));
                        while (dayEnd.before(nextDate) || dayEnd.equals(nextDate)) {
                            event = new TimeClockEvent(employee, false, (Calendar) dayEnd.clone(), null);
                            System.out.println("\t(case 2) adding new DEPARTURE time clock event: " + event);
                            events.add(event);
                            dayStart.add(Calendar.DAY_OF_MONTH, 1);
                            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                            System.out.println("DAY start: " + CalendarFormat.format(dayStart) + ", end: " + CalendarFormat.format(dayEnd));
                            event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                            System.out.println("\t(case 2) adding new ARRIVAL time clock event: " + event);
                            events.add(event);
                        }
                    }
                    
                }
                previousDate = nextDate;           
            }
            
            event = employeeEvents.get(employeeEvents.size() - 1);
            if (!(event instanceof TimeClockEvent) || ((TimeClockEvent) event).isArrival()) {
                while (dayStart.after(previousDate) || dayEnd.before(previousDate)) {
                    dayStart.add(Calendar.DAY_OF_MONTH, 1);
                    dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                }
                event = new TimeClockEvent(employee, false, (Calendar) dayEnd.clone(), null);
                System.out.println("\t(case 3) adding new DEPARTURE time clock event: " + event);
                events.add(event);
            }
            
            
            System.out.println("\n" + employee + "'s events after:");
            for (AbstractEmployeeRelatedEvent employeeEvent : employeeEvents) {
                System.out.println("\t" + employeeEvent);
            }
        }
        Collections.sort(events);
        System.out.println("After preparation: (size: " + events.size() + ")");
        for (AbstractEmployeeRelatedEvent e : events) {
            System.out.println("\t" + e);
        }
    }
    
    private void build() throws ReportException {
        FindByEmployee filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            builders.add(new EmployeeEventsPeriodBuilder(employee, filter.getItems()));
        }
    }
    
    public EmployeeEventsPeriodBuilder get(Employee employee) {
        for (EmployeeEventsPeriodBuilder builder : this) {
            if (employee.equals(builder.getEmployee())) {
                return builder;
            }
        }
        return null;
    }

    @Override
    public Iterator<EmployeeEventsPeriodBuilder> iterator() {
        return builders.iterator();
    }
    
}
