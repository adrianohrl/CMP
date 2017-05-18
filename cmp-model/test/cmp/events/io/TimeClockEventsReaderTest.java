/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.control.model.events.io.TimeClockEventsReader;
import cmp.exceptions.IOException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.EntryEvent;
import cmp.model.personal.Employee;
import cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import cmp.control.model.production.reports.filters.FindByEmployee;
import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderTest {
    
    public static void main(String[] args) throws IOException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        TimeClockEventsReader reader = new TimeClockEventsReader(fileName);
        reader.readFile();
        EmployeeRelatedEventsList<EntryEvent> events = reader.getEmployeeRelatedEventsList();
        FindByEmployee<EntryEvent> filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee<>(employee);
            events.execute(filter);
            System.out.println("Employee: " + employee);
            int counter = 0;
            for (Object event : filter) {
                System.out.println("Event " + counter++ + ": " + event);
            }
            System.out.println("\n---------\n");
        }
    }
    
}
