/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.control.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.production.reports.filters.FindByEmployee;
import tech.adrianohrl.stile.control.production.reports.filters.FindByPeriod;
import tech.adrianohrl.stile.exceptions.StileException;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class FindByPeriodTest {
    
    public static void main(String[] args) throws StileException {
        Subordinate subordinate1 = new Subordinate("sub1", "Subordinate 1");
        Subordinate subordinate2 = new Subordinate("sub2", "Subordinate 2");
        Subordinate subordinate3 = new Subordinate("sub3", "Subordinate 3");
        
        List<TimeClockEvent> timeClockEvents = new ArrayList<>();
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 2, 17, 1), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 3, 7, 1), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, true, new GregorianCalendar(2017, 4, 3, 7, 0), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate3, true, new GregorianCalendar(2017, 4, 3, 8, 30), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 3, 11, 2), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, false, new GregorianCalendar(2017, 4, 3, 11, 5), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate3, false, new GregorianCalendar(2017, 4, 3, 11, 15), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 3, 12, 45), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, true, new GregorianCalendar(2017, 4, 3, 13, 0), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate3, true, new GregorianCalendar(2017, 4, 3, 13, 0), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 3, 17, 42), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, false, new GregorianCalendar(2017, 4, 3, 17, 50), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate3, false, new GregorianCalendar(2017, 4, 3, 18, 15), ""));
        
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 4, 7, 1), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 4, 12, 10), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 5, 8, 30), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 5, 11, 2), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, true, new GregorianCalendar(2017, 4, 5, 11, 5), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, false, new GregorianCalendar(2017, 4, 5, 11, 15), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, true, new GregorianCalendar(2017, 4, 6, 12, 45), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, false, new GregorianCalendar(2017, 4, 6, 13, 0), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 7, 13, 0), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 7, 17, 42), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, true, new GregorianCalendar(2017, 4, 8, 17, 50), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate2, false, new GregorianCalendar(2017, 4, 8, 18, 15), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 8, 20, 50), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 9, 8, 15), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 10, 17, 59), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, false, new GregorianCalendar(2017, 4, 12, 5, 15), ""));
        timeClockEvents.add(new TimeClockEvent(subordinate1, true, new GregorianCalendar(2017, 4, 13, 12, 01), ""));

        Employee employee = subordinate1;
        EmployeeRelatedEventsList<TimeClockEvent> events = new EmployeeRelatedEventsList<>();
        events.addAll(timeClockEvents);
        FindByEmployee<TimeClockEvent> filterByEmployee = new FindByEmployee<>(employee);
        events.execute(filterByEmployee);        
        FindByPeriod<TimeClockEvent> filterByPeriod = new FindByPeriod<>(new GregorianCalendar(2017, 4, 1), new GregorianCalendar(2017, 4, 13, 23, 59, 59));
        filterByEmployee.getItems().execute(filterByPeriod);
        System.out.println("\nBefore filterByEmployee: ");
        for (TimeClockEvent event : events) {
            System.out.println(event);
        }
        System.out.println("\nAfter filterByEmployee: ");
        for (TimeClockEvent event : filterByEmployee) {
            System.out.println(event);
        }
        System.out.println("\nAfter filterByPeriod: ");
        for (TimeClockEvent event : filterByPeriod) {
            System.out.println(event);
        }
    }
    
}
