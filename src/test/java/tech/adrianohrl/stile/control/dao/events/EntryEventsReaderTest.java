/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.model.events.io.EntryEventsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.control.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.production.reports.filters.FindByEmployee;
import tech.adrianohrl.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsReaderTest {
    
    public static void main(String[] args) throws IOException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        EntryEventsReader reader = new EntryEventsReader();
        reader.readFile(fileName);
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
