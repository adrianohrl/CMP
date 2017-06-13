/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.control.model.events.io.EntryEventsReader;
import br.com.ceciliaprado.cmp.control.model.events.io.TimeClockEventsReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.control.model.production.reports.EmployeeEventsPeriodBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.EventsPeriodBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class EventsReaderTest {
    
    public static void main(String[] args) throws IOException, ReportException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the time clock events file name: ");
        EntryEventsReader entryEventsReader = new EntryEventsReader();
        entryEventsReader.readFile(fileName);
        fileName = keyboard.readString("Enter the time clock events file name: ");
        TimeClockEventsReader timeClockEventsReader = new TimeClockEventsReader();
        timeClockEventsReader.readFile(fileName);
        
        EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
        events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
        events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
        
        EventsPeriodBuilder builder = new EventsPeriodBuilder(events);
        for (EmployeeEventsPeriodBuilder b : builder) {
            System.out.println("Employee: " + b.getEmployee());
            for (ModelPhase phase : b.getPhases()) {
                System.out.println("\tPhase: " + phase);
                System.out.println("\t\tEffective Duration: " + b.getEffectiveDuration(phase) + " [min]");
                System.out.println("\t\tExpected Duration: " + b.getExpectedDuration(phase) + " [min]");
                System.out.println("\t\tProduced Quantity: " + b.getProducedQuantity(phase) + " [un]");
                System.out.println("\t\tReturned Quantity: " + b.getReturnedQuantity(phase) + " [un]");
                System.out.println("\t\tEffective Efficiency: " + (b.getEffectiveEfficiency(phase) * 100) + " %");
            }
            System.out.println("\n\t-------------------------------------------------------------\n");
            System.out.println("\tTotals:");
            System.out.println("\t\tEffective Duration: " + b.getTotalEffectiveDuration() + " [min]");
            System.out.println("\t\tExpected Duration: " + b.getTotalExpectedDuration() + " [min]");
            System.out.println("\t\tFree Duration: " + b.getTotalFreeDuration() + " [min]");
            System.out.println("\t\tTotal Duration: " + b.getTotalDuration() + " [min]");
            System.out.println("\t\tProduced Quantity: " + b.getTotalProducedQuantity() + " [un]");
            System.out.println("\t\tReturned Quantity: " + b.getTotalReturnedQuantity() + " [un]");
            System.out.println("\t\tEffective Efficiency: " + (b.getTotalEffectiveEfficiency() * 100) + " %");
            System.out.println("\t\tTotal Efficiency: " + (b.getTotalEfficiency() * 100) + " %");
            System.out.println("\n=====================================================================\n");
        }
    }
    
}
