/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.control.dao.DataSource;
import tech.adrianohrl.stile.control.dao.events.io.EntryEventsReaderDAO;
import tech.adrianohrl.stile.control.dao.events.io.TimeClockEventsReaderDAO;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.stile.util.Keyboard;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

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
            System.out.println("RuntimeException caught:" + e.getMessage());
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
            /*EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(timeClockEventsReader.getEmployeeRelatedEventsList());
            events.addAll(entryEventsReader.getEmployeeRelatedEventsList());
            EventsTest.register(events);*/
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        } 
    }

    private static void createCasualties() {
        casualties = new ArrayList<>();
        casualties.add(new Casualty("Falta de matéria-prima"));
        casualties.add(new Casualty("Realocação"));
        casualties.add(new Casualty("Falta de energia elétrica", true));
    }
    
}
