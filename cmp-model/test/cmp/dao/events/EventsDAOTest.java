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
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.CasualtyEntryEvent;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventsDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        Keyboard keyboard = Keyboard.getKeyboard();
        try {
            String fileName = "../others/tests/ImportTimeClockEvents1.csv";
            //keyboard.readString("Enter the file name: ");
            TimeClockEventsReaderDAO timeClockEventsReader = new TimeClockEventsReaderDAO(em, fileName);
            timeClockEventsReader.readFile();
            //fileName = keyboard.readString("Enter the file name: ");
            fileName = "../others/tests/ImportEntryEvents1.csv";
            EntryEventsReaderDAO entryEventsReader = new EntryEventsReaderDAO(em, fileName);
            entryEventsReader.readFile();
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
            events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
            EventsDAOTest.register(events);
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
        } catch (IOException ioe) {
            System.out.println("IO exception catched: " + ioe.getMessage());
        } finally {
            em.close();
            DataSource.closeEntityManagerFactory();
        }
    }
    
    private static void register(EmployeeRelatedEventsList events) {
        for (AbstractEmployeeRelatedEvent event : events) {
            EventsDAOTest.register(event);
        }
    }
    
    private static void register(AbstractEmployeeRelatedEvent event) {
        if (event instanceof TimeClockEvent) {
            TimeClockEventDAO timeClockEventDAO = new TimeClockEventDAO(em);
            timeClockEventDAO.create((TimeClockEvent) event);
        } else if (event instanceof CasualtyEntryEvent) {
            CasualtyEntryEventDAO casualtyEntryEventDAO = new CasualtyEntryEventDAO(em);
            casualtyEntryEventDAO.create((CasualtyEntryEvent) event);
        } else if (event instanceof EntryEvent) {
            EntryEventDAO entryEventDAO = new EntryEventDAO(em);
            entryEventDAO.create((EntryEvent) event);
        } 
    }
    
}
