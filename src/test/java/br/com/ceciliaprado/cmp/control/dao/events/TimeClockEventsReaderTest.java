/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.control.model.events.io.TimeClockEventsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByEmployee;
import br.com.ceciliaprado.cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderTest {
    
    public static void main(String[] args) throws IOException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        TimeClockEventsReader reader = new TimeClockEventsReader();
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
