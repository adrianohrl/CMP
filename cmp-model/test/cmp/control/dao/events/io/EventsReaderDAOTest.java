/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.events.io;

import cmp.control.dao.DataSource;
import cmp.exceptions.IOException;
import cmp.model.events.Casualty;
import cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventsReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        Keyboard keyboard = Keyboard.getKeyboard();
        CasualtiesReaderDAO casualtyReader = new CasualtiesReaderDAO(em);
        String fileName;
        try {
            System.out.println("Testing the CasualtiesReaderDAO class ...");
            fileName = "../others/tests/ImportCasualties1.csv";//keyboard.readString("Enter the file name: ");
            casualtyReader.readFile(fileName);
            System.out.println("  The following casualties were registered:");
            for (Casualty casualty : casualtyReader) {
                System.out.println("\t" + casualty);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception catched: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
