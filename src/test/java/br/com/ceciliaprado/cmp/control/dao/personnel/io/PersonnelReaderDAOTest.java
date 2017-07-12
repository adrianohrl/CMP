/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel.io;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonnelReaderDAOTest {
    
    private final static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        PersonnelReaderDAOTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) {
        Keyboard keyboard = Keyboard.getKeyboard();
        PersonnelReaderDAO personnelReader = new PersonnelReaderDAO(em); 
        SubordinatesReaderDAO subordinatesReader = new SubordinatesReaderDAO(em);
        SupervisorsReaderDAO supervisorsReader = new SupervisorsReaderDAO(em);
        SectorsReaderDAO sectorsReader = new SectorsReaderDAO(em);
        String fileName;
        try {
            System.out.println("Testing the PersonnelReaderDAO class ...");
            fileName = "./others/tests/ImportPersonnel1.csv";//keyboard.readString("Enter the file name: ");
            personnelReader.readFile(fileName);
            System.out.println("  The following employees were registered:");
            for (Employee employee : personnelReader) {
                System.out.println("\t" + employee);
            }
            System.out.println("\n\nTesting the SubordinatesReaderDAO class ...");
            fileName = "./others/tests/ImportSubordinates1.csv";//keyboard.readString("Enter the file name: ");
            subordinatesReader.readFile(fileName);
            System.out.println("  The following supervisors were updated:");
            for (Supervisor supervisor : subordinatesReader) {
                System.out.println("\t" + supervisor);
            }
            System.out.println("\n\nTesting the SupervisorsReaderDAO class ...");
            fileName = "./others/tests/ImportSupervisors1.csv";//keyboard.readString("Enter the file name: ");
            supervisorsReader.readFile(fileName);
            System.out.println("  The following managers were updated:");
            for (Manager manager : supervisorsReader) {
                System.out.println("\t" + manager);
            }
            System.out.println("\n\nTesting the SectorsReaderDAO class ...");
            fileName = "./others/tests/ImportSectors1.csv";//keyboard.readString("Enter the file name: ");
            sectorsReader.readFile(fileName);
            System.out.println("  The following sectors were registered:");
            for (Sector sector : sectorsReader) {
                System.out.println("\t" + sector);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception catched: " + e.getMessage());
        }
    }
    
}
