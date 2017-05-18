/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.control.model.events.io.TimeClockEventsReader;
import cmp.control.model.events.io.EntryEventsReader;
import cmp.exceptions.IOException;
import cmp.exceptions.ReportException;
import cmp.model.production.Phase;
import cmp.control.model.production.reports.EmployeeEventsPeriodBuilder;
import cmp.control.model.production.reports.EventsPeriodBuilder;
import cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public class EventsReaderTest {
    
    public static void main(String[] args) throws IOException, ReportException {
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the time clock events file name: ");
        EntryEventsReader entryEventsReader = new EntryEventsReader(fileName);
        entryEventsReader.readFile();
        fileName = keyboard.readString("Enter the time clock events file name: ");
        TimeClockEventsReader timeClockEventsReader = new TimeClockEventsReader(fileName);
        timeClockEventsReader.readFile();
        
        EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
        events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
        events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
        
        EventsPeriodBuilder builder = new EventsPeriodBuilder(events);
        for (EmployeeEventsPeriodBuilder b : builder) {
            System.out.println("Employee: " + b.getEmployee());
            for (Phase phase : b.getPhases()) {
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
