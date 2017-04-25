/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personal;

import cmp.dao.DataSource;
import cmp.model.personal.Employee;
import cmp.model.personal.Manager;
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonalDAOsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    private static Map<String, Subordinate> subordinates;
    private static Map<String, Supervisor> supervisors;
    private static Map<String, Manager> managers;
    private static Map<String, Sector> sectors;
    
    public static void main(String[] args) {
        try {
            PersonalDAOsTest.createEmployees();
            PersonalTest.registerEmployees(subordinates.values());
            PersonalTest.registerEmployees(supervisors.values());
            PersonalTest.registerEmployees(managers.values());
            PersonalDAOsTest.createSectors();
            PersonalTest.registerSectors(sectors.values());
            PersonalTest.showAllRegisteredSubordinates();
            PersonalTest.showAllRegisteredSupervisors();
            PersonalTest.showAllRegisteredManagers();
            PersonalTest.showAllRegisteredSectors();
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
        } finally {
            em.close();
            DataSource.closeEntityManagerFactory();
        }
    }
    
    private static void createEmployees() {
        List<Employee> employees = new ArrayList<>();
        /************************* Subordinates *************************/
        subordinates = new HashMap<>();
        subordinates.put("Joaquina", new Subordinate("00001", "Joaquina"));
        subordinates.put("Joaquim", new Subordinate("00002", "Joaquim"));
        subordinates.put("Rosana", new Subordinate("00003", "Rosana"));
        subordinates.put("Rose", new Subordinate("00004", "Rose"));
        subordinates.put("Maria", new Subordinate("00005", "Maria"));
        subordinates.put("João", new Subordinate("00006", "João"));
        subordinates.put("José", new Subordinate("00007", "José"));
        subordinates.put("Roseli", new Subordinate("00008", "Roseli"));
        subordinates.put("Marcelo", new Subordinate("00009", "Marcelo"));
        subordinates.put("Murilo", new Subordinate("00010", "Murilo"));
        employees.addAll(subordinates.values());
        /************************* Supervisors *************************/
        supervisors = new HashMap<>();
        Supervisor supervisor = new Supervisor("juh", "123456", "00011", "Juliane");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Joaquina"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Rosana"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("José"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Murilo"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("ale", "123", "00012", "Alessandro");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Rose"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Maria"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("julio", "1234", "00013", "Julio");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Joaquim"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Marcelo"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("ana", "123456789", "00014", "Ana");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("João"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Roseli"));
        supervisors.put(supervisor.getName(), supervisor);
        employees.addAll(supervisors.values());
        /************************* Managers *************************/
        managers = new HashMap<>();
        Manager manager = new Manager("rod", "147258369", "00015", "Rodrigo");
        manager.getSupervisors().add(supervisors.get("Juliane"));
        manager.getSupervisors().add(supervisors.get("Ana"));
        managers.put(manager.getName(), manager);
        manager = new Manager("rafa", "147258", "00016", "Rafaela");
        manager.getSupervisors().add(supervisors.get("Alessandro"));
        manager.getSupervisors().add(supervisors.get("Julio"));
        managers.put(manager.getName(), manager);
        employees.addAll(managers.values());
        Collections.sort(employees);
        PersonalTest.print(employees);
    }

    private static void createSectors() {
        sectors = new HashMap<>();
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        Supervisor supervisor = supervisorDAO.find("Alessandro");
        Sector sector = new Sector("Passadoria", supervisor);
        sectors.put(sector.getName(), sector);
        supervisor = supervisorDAO.find("Ana");
        sector = new Sector("Costura", supervisor);
        sectors.put(sector.getName(), sector);
        supervisor = supervisorDAO.find("Julio");
        sector = new Sector("Tecimento", supervisor);
        sectors.put(sector.getName(), sector);
        supervisor = supervisorDAO.find("Juliane");
        sector = new Sector("Corte", supervisor);
        sectors.put(sector.getName(), sector);
    }
    
}
