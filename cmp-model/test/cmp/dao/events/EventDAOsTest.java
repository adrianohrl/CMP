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
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        try {
            TimeClockEventsReaderDAO timeClockEventsReader = new TimeClockEventsReaderDAO(em, fileName);
            timeClockEventsReader.readFile();
            fileName = keyboard.readString("Enter the file name: ");
            EntryEventsReaderDAO entryEventsReader = new EntryEventsReaderDAO(em, fileName);
            entryEventsReader.readFile();
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
            events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
            EventsTest.register(events);
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
        } catch (IOException ioe) {
            System.out.println("IO exception catched: " + ioe.getMessage());
        } finally {
            em.close();
            DataSource.closeEntityManagerFactory();
        }
    }    
    
}
