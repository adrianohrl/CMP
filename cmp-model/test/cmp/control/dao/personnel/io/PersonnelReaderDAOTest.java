/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personnel.io;

import cmp.control.dao.DataSource;
import cmp.exceptions.IOException;
import cmp.model.personnel.Employee;
import cmp.model.personnel.Manager;
import cmp.model.personnel.Sector;
import cmp.model.personnel.Supervisor;
import cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonnelReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) throws IOException {
        System.out.println("Testing the PersonnelReaderDAO ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        PersonnelReaderDAO personnelReader = new PersonnelReaderDAO(em); 
        SubordinatesReaderDAO subordinatesReader = new SubordinatesReaderDAO(em);
        SupervisorsReaderDAO supervisorsReader = new SupervisorsReaderDAO(em);
        SectorsReaderDAO sectorsReader = new SectorsReaderDAO(em);
        String fileName = "../others/tests/ImportPersonnel1.csv";//keyboard.readString("Enter the file name: ");
        try {
            personnelReader.readFile(fileName);
            System.out.println("\nThe following employees were registered:");
            for (Employee employee : personnelReader) {
                System.out.println("\t" + employee);
            }
            fileName = "../others/tests/ImportSubordinates1.csv";//keyboard.readString("Enter the file name: ");
            subordinatesReader.readFile(fileName);
            System.out.println("\nThe following supervisors were updated:");
            for (Supervisor supervisor : subordinatesReader) {
                System.out.println("\t" + supervisor);
            }
            fileName = "../others/tests/ImportSupervisors1.csv";//keyboard.readString("Enter the file name: ");
            supervisorsReader.readFile(fileName);
            System.out.println("\nThe following managers were updated:");
            for (Manager manager : supervisorsReader) {
                System.out.println("\t" + manager);
            }
            fileName = "../others/tests/ImportSectors1.csv";//keyboard.readString("Enter the file name: ");
            sectorsReader.readFile(fileName);
            System.out.println("\nThe following sectors were registered:");
            for (Sector sector : sectorsReader) {
                System.out.println("\t" + sector);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception catched: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
