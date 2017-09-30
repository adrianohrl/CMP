/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.CMPException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adrianohrl
 */
public class AttendanceReportTest {
    
    public static void main(String[] args) throws CMPException {
        Manager manager = new Manager("mag1", "Manager 1", "mag1", "mag1");
        Subordinate subordinate1 = new Subordinate("sub1", "Subordinate 1");
        Subordinate subordinate2 = new Subordinate("sub2", "Subordinate 2");
        Subordinate subordinate3 = new Subordinate("sub3", "Subordinate 3");
        List<Employee> employees = new ArrayList<>();
        employees.add(subordinate1);
        employees.add(subordinate2);
        employees.add(subordinate3);
        
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

        EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events = new EmployeeRelatedEventsList<>();
        events.addAll(timeClockEvents);  
        Calendar startDate = new GregorianCalendar(2017, 4, 1);
        Calendar endDate = new GregorianCalendar(2017, 4, 19);
        endDate.add(Calendar.MILLISECOND, -1);
        for (Employee employee : employees) {    
            EmployeeAttendanceReport report = new EmployeeAttendanceReport(employee, events, manager, startDate, endDate);
            ReportNumericSeries<AttendanceSeriesTypes> series = report.getSeries(AttendanceSeriesTypes.TOTAL_QUANTITY);
            System.out.println("\n\tDaily " + series + ":");
            for (Map.Entry<Calendar, Number> entry : series) {
                System.out.println("\t\t" + series.format(entry));
            }
            System.out.println("\t\t-----------------------");
            System.out.println("\t\tPeriod total: " + series.format(series.getTotal()));
        }
        
    }
    
}
