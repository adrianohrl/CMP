/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.util.Keyboard;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonnelTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        PersonnelMenuOptions option = PersonnelMenuOptions.getOption();
        while (!option.quit()) {
            try {
                PersonnelTest.process(option);
            } catch (RuntimeException e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
            option = PersonnelMenuOptions.getOption();
        }
        System.out.println("Quitting!!!");
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void process(PersonnelMenuOptions option) {
        if (option.quit()) {
            return;
        }
        switch (option) {
            case REGISTER_SUBORDINATE:
                PersonnelTest.createSubordinate();
                break;
            case REGISTER_SUPERVISOR:
                PersonnelTest.createSupervisor();
                break;
            case REGISTER_MANAGER:
                PersonnelTest.createManager();
                break;
            case REGISTER_SECTOR:
                PersonnelTest.createSector();
                break;
            case SHOW_ALL_SUBORDINATES:
                PersonnelTest.showAllRegisteredSubordinates();
                break;
            case SHOW_ALL_SUPERVISORS:
                PersonnelTest.showAllRegisteredSupervisors();
                break;
            case SHOW_ALL_MANAGERS:
                PersonnelTest.showAllRegisteredManagers();
                break;
            case SHOW_ALL_SECTORS:
                PersonnelTest.showAllRegisteredSectors();
                break;
            case ASSIGN_NEW_SUBORDINATES:
                PersonnelTest.assignNewSubordinatesToSupervisor();
                break;
            case ASSIGN_NEW_SUPERVISORS:
                PersonnelTest.assignNewSupervisorsToManager();
                break;
            case UPGRADE_EMPLOYEE:
                PersonnelTest.upgradeEmployee();
                break;
            case DOWNGRADE_EMPLOYEE:
                PersonnelTest.downgradeEmployee();
                break;
            default:
                System.out.println("Invalid option!!!");
        }
    }
    
    public static void registerEmployees(Collection<? extends Employee> employees) {
        for (Employee employee : employees) {
            PersonnelTest.register(employee);
        }
    }
    
    public static void register(Employee employee) {
        if (employee instanceof Subordinate) {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinateDAO.create((Subordinate) employee);
        } else if (employee instanceof Supervisor) {
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            supervisorDAO.create((Supervisor) employee);
        } else if (employee instanceof Manager) {
            ManagerDAO managerDAO = new ManagerDAO(em);
            managerDAO.create((Manager) employee);
        }
    }
    
    public static void registerSectors(Collection<Sector> sectors) {
        for (Sector sector : sectors) {
            PersonnelTest.register(sector);
        }
    }
    
    public static void register(Sector sector) {
        SectorDAO sectorDAO = new SectorDAO(em);
        sectorDAO.create(sector);
    }

    public static void showAllRegisteredSubordinates() {
        System.out.println("Showing all registered subordinates ...");
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        PersonnelTest.print(subordinateDAO.findAll());
    }

    public static void showAllRegisteredSupervisors() {
        System.out.println("Showing all registered supervisors ...");
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        PersonnelTest.print(supervisorDAO.findAll());
    }

    public static void showAllRegisteredManagers() {
        System.out.println("Showing all registered managers ...");
        ManagerDAO managerDAO = new ManagerDAO(em);
        PersonnelTest.print(managerDAO.findAll());
    }

    public static void showAllRegisteredSectors() {
        System.out.println("Showing all registered sectors ...");
        SectorDAO sectorDAO = new SectorDAO(em);
        List<Sector> sectors = sectorDAO.findAll();
        if (sectors.isEmpty()) {
            System.out.println("No records found!!!");
        }
        for (Sector sector : sectors) {
            System.out.println("Sector: " + sector);
            System.out.println("\tSupervisor: " + sector.getSupervisor());
        }
    }
    
    public static void print(List<? extends Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No records found!!!");
        }
        for (Employee employee : employees) {
            if (employee instanceof Subordinate) {
                Subordinate subordinate = (Subordinate) employee;
                System.out.println("Subordinate: " + subordinate);
            } else if (employee instanceof Supervisor) {
                Supervisor supervisor = (Supervisor) employee;
                System.out.println("Supervisor: " + supervisor);
                System.out.println("\tSubordinates:");
                for (Subordinate subordinate : supervisor.getSubordinates()) {
                    System.out.println("\t\t" + subordinate);
                }
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                System.out.println("Manager: " + manager);
                System.out.println("\tSupervisors:");
                for (Supervisor supervisor : manager.getSupervisors()) {
                    System.out.println("\t\t" + supervisor);
                }
            }
        }
    }

    private static void createSubordinate() {
        System.out.println("\nRegistering a new subordinate ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new subordinate below:");
        String code = keyboard.readString("code: ");
        String name = keyboard.readString("name: ");
        try {
            PersonnelTest.register(new Subordinate(code, name));
            System.out.println("The subordinate registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The subordinate registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createSupervisor() {
        System.out.println("\nRegistering a new supervisor ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new supervisor below:");
        String code = keyboard.readString("code: ");
        String name = keyboard.readString("name: ");
        String login = keyboard.readString("login: ");
        String password = keyboard.readString("password: ");
        try {
            Supervisor supervisor = new Supervisor(login, password, code, name);
            PersonnelTest.register(supervisor);
            PersonnelTest.assignNewSubordinatesToSupervisor(supervisor);
            System.out.println("The supervisor registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The supervisor registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createManager() {
        System.out.println("\nRegistering a new manager ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new manager below:");
        String code = keyboard.readString("code: ");
        String name = keyboard.readString("name: ");
        String login = keyboard.readString("login: ");
        String password = keyboard.readString("password: ");
        try {
            Manager manager = new Manager(login, password, code, name);
            PersonnelTest.register(manager);
            assignNewSupervisorsToManager(manager);
            System.out.println("The manager registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The manager registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createSector() {
        System.out.println("\nResgistering a new sector ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new sector below:");
        String name = keyboard.readString("name: ");
        Supervisor supervisor = PersonnelKeyboardEntries.selectOneSupervisor();
        try {
            PersonnelTest.register(new Sector(name, supervisor));
            System.out.println("The sector registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The sector registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void assignNewSubordinatesToSupervisor() {
        System.out.println("\nAssigning new subordinates to a supervisor ...");
        Supervisor supervisor = PersonnelKeyboardEntries.selectOneSupervisor();
        assignNewSubordinatesToSupervisor(supervisor);
    }
    
    public static void assignNewSubordinatesToSupervisor(Supervisor supervisor) {
        List<Subordinate> subordinates = PersonnelKeyboardEntries.selectManySubordinates(supervisor);
        if (subordinates == null) {
            return;
        }
        supervisor.getSubordinates().addAll(subordinates);
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        try {
            supervisorDAO.update(supervisor);
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
            em.clear();
        }
    }

    private static void assignNewSupervisorsToManager() {
        System.out.println("\nAssigning new supervisors to a manager ...");
        Manager manager = PersonnelKeyboardEntries.selectOneManager();
        assignNewSupervisorsToManager(manager);
    }

    public static void assignNewSupervisorsToManager(Manager manager) {
        List<Supervisor> supervisors = PersonnelKeyboardEntries.selectManySupervisors(manager);
        if (supervisors == null) {
            return;
        }
        manager.getSupervisors().addAll(supervisors);
        ManagerDAO managerDAO = new ManagerDAO(em);
        try {
            managerDAO.update(manager);
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
            em.clear();
        }
    }

    private static void upgradeEmployee() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void downgradeEmployee() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
