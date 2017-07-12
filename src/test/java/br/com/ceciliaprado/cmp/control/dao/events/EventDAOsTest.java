/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.io.EntryEventsReaderDAO;
import br.com.ceciliaprado.cmp.control.dao.events.io.TimeClockEventsReaderDAO;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.util.Keyboard;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

/**
 *
 * @author adrianohrl
 */
public class EventDAOsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    private static List<Casualty> casualties;
    
    public static void main(String[] args) {
        EventDAOsTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }    
    
    public static void test(EntityManager em) {
        try {
            EventDAOsTest.createCasualties();
            EventsTest.registerCasualties(casualties);
            System.out.println("");
            EventsTest.showAllCasualties();
            System.out.println("");
            EventsTest.showAllCollectiveCasualties();
            System.out.println("");
            EventsTest.showAllNonCollectiveCasualties();
        } catch (RuntimeException e) {
            System.out.println("RuntimeException catched:" + e.getMessage());
        }
        try {
            Keyboard keyboard = Keyboard.getKeyboard();
            //String fileName = keyboard.readString("Enter the file name to import time clock events: ");
            String fileName = "./others/tests/ImportTimeClockEvents1.csv";
            TimeClockEventsReaderDAO timeClockEventsReader = new TimeClockEventsReaderDAO(em);
            timeClockEventsReader.readFile(fileName);
            //fileName = keyboard.readString("Enter the file name to import entry events: ");
            fileName = "./others/tests/ImportEntryEvents1.csv";
            EntryEventsReaderDAO entryEventsReader = new EntryEventsReaderDAO(em);
            entryEventsReader.readFile(fileName);
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
            events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
            EventsTest.register(events);
        } catch (RollbackException e) {
            System.out.println("RollbackException catched: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException catched: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("RuntimeException catched: " + e.getMessage());
        }
    }

    private static void createCasualties() {
        casualties = new ArrayList<>();
        casualties.add(new Casualty("Falta de matéria-prima"));
        casualties.add(new Casualty("Realocação"));
        casualties.add(new Casualty("Falta de energia elétrica", true));
    }
    
}
