/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.dao.DataSource;
import cmp.dao.events.io.EntryEventsReaderDAO;
import cmp.dao.events.io.TimeClockEventsReaderDAO;
import cmp.exceptions.IOException;
import cmp.exceptions.ProductionStateMachineException;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventDAOsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        try {
            Keyboard keyboard = Keyboard.getKeyboard();
            //String fileName = keyboard.readString("Enter the file name to import time clock events: ");
            String fileName = "../others/tests/ImportTimeClockEvents1.csv";
            TimeClockEventsReaderDAO timeClockEventsReader = new TimeClockEventsReaderDAO(em, fileName);
            timeClockEventsReader.readFile();
            //fileName = keyboard.readString("Enter the file name to import entry events: ");
            fileName = "../others/tests/ImportEntryEvents1.csv";
            EntryEventsReaderDAO entryEventsReader = new EntryEventsReaderDAO(em, fileName);
            entryEventsReader.readFile();
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
            events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
            EventsTest.register(events);
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception catched: " + e.getMessage());
        } catch (ProductionStateMachineException e) {
            System.out.println("Production State Machine exception catched: " + e.getMessage());
        } finally {
            em.close();
            DataSource.closeEntityManagerFactory();
        }
    }    
    
}
