/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events.io;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventsReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        EventsReaderDAOTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) {
        Keyboard keyboard = Keyboard.getKeyboard();
        CasualtiesReaderDAO casualtyReader = new CasualtiesReaderDAO(em);
        EntryEventsReaderDAO entryEventsReaderDAO = new EntryEventsReaderDAO(em);
        TimeClockEventsReaderDAO timeClockEventsReaderDAO = new TimeClockEventsReaderDAO(em);
        String fileName;
        try {
            System.out.println("Testing the CasualtiesReaderDAO class ...");
            fileName = "./others/tests/ImportCasualties1.csv";//keyboard.readString("Enter the file name: ");
            casualtyReader.readFile(fileName);
            System.out.println("  The following casualties were registered:");
            for (Casualty casualty : casualtyReader) {
                System.out.println("\t" + casualty);
            }
            System.out.println("\n\nTesting the TimeClockEventsReaderDAO class ...");
            fileName = "./others/tests/ImportTimeClockEvents1.csv";//keyboard.readString("Enter the file name: ");
            timeClockEventsReaderDAO.readFile(fileName);
            System.out.println("  The following time clock events were registered:");
            for (TimeClockEvent event : timeClockEventsReaderDAO) {
                System.out.println("\t" + event);
            }
            System.out.println("\n\nTesting the EntryEventsReaderDAO class ...");
            fileName = "./others/tests/ImportEntryEvents1.csv";//keyboard.readString("Enter the file name: ");
            entryEventsReaderDAO.readFile(fileName);
            System.out.println("  The following entry events were registered:");
            for (EntryEvent event : entryEventsReaderDAO) {
                System.out.println("\t" + event);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
    
}
