/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.cmp.events.io;

import cmp.events.io.TimeClockEventsReader;
import cmp.exceptions.IOException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.personal.Employee;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.production.reports.filters.FindByEmployee;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderTest {
    
    public static void main(String[] args) throws IOException {
        TimeClockEventsReader reader = new TimeClockEventsReader("tests/ImportTimeClockEvents1.csv");
        EmployeeRelatedEventsList events = new EmployeeRelatedEventsList(reader.getTimeClockEvents());
        FindByEmployee filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            System.out.println("Employee: " + employee);
            int counter = 0;
            for (AbstractEmployeeRelatedEvent event : filter) {
                System.out.println("Event " + counter + ": " + event);
            }
            System.out.println("\n---------\n");
        }
        
    }
    
}
