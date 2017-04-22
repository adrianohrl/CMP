/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.cmp.events.io;

import cmp.events.io.EntryEventsReader;
import cmp.exceptions.IOException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.personal.Employee;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.production.reports.filters.FindByEmployee;
import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsReaderTest {
    
    public static void main(String[] args) throws IOException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        EntryEventsReader reader = new EntryEventsReader(fileName);
        EmployeeRelatedEventsList events = reader.getEmployeeRelatedEventsList();
        FindByEmployee filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            System.out.println("Employee: " + employee);
            int counter = 0;
            for (AbstractEmployeeRelatedEvent event : filter) {
                System.out.println("Event " + counter++ + ": " + event);
            }
            System.out.println("\n---------\n");
        }
    }
    
}
