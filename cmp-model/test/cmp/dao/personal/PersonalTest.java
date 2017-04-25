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
import cmp.util.Keyboard;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonalTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        MenuOptions option = MenuOptions.getOption();
        while (!option.quit()) {
            try {
                process(option);
            } catch (RuntimeException e) {
                System.out.println("Exception catched: " + e.getMessage());
            }
            option = MenuOptions.getOption();
        }
        System.out.println("Quitting!!!");
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    private static void process(MenuOptions option) {
        if (option.quit()) {
            return;
        }
        switch (option) {
            case REGISTER_SUBORDINATE:
                PersonalTest.createSubordinate();
                break;
            case REGISTER_SUPERVISOR:
                PersonalTest.createSupervisor();
                break;
            case REGISTER_MANAGER:
                PersonalTest.createManager();
                break;
            case REGISTER_SECTOR:
                PersonalTest.createSector();
                break;
            case SHOW_ALL_SUBORDINATES:
                PersonalTest.showAllRegisteredSubordinates();
                break;
            case SHOW_ALL_SUPERVISORS:
                PersonalTest.showAllRegisteredSupervisors();
                break;
            case SHOW_ALL_MANAGERS:
                PersonalTest.showAllRegisteredManagers();
                break;
            case SHOW_ALL_SECTORS:
                PersonalTest.showAllRegisteredSectors();
                break;
            case ASSIGN_NEW_SUBORDINATES:
                PersonalTest.assignNewSubordinatesToSupervisor();
                break;
            case ASSIGN_NEW_SUPERVISORS:
                PersonalTest.assignNewSupervisorsToManager();
                break;
            case UPGRADE_EMPLOYEE:
                PersonalTest.upgradeEmployee();
                break;
            case DOWNGRADE_EMPLOYEE:
                PersonalTest.downgradeEmployee();
                break;
            default:
                System.out.println("Invalid option!!!");
        }
    }
    
    public static void registerEmployees(Collection<? extends Employee> employees) {
        for (Employee employee : employees) {
            PersonalTest.register(employee);
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
            PersonalTest.register(sector);
        }
    }
    
    public static void register(Sector sector) {
        SectorDAO sectorDAO = new SectorDAO(em);
        sectorDAO.create(sector);
    }

    public static void showAllRegisteredSubordinates() {
        System.out.println("Showing all registered subordinates ...");
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        PersonalTest.print(subordinateDAO.findAll());
    }

    public static void showAllRegisteredSupervisors() {
        System.out.println("Showing all registered supervisors ...");
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        PersonalTest.print(supervisorDAO.findAll());
    }

    public static void showAllRegisteredManagers() {
        System.out.println("Showing all registered managers ...");
        ManagerDAO managerDAO = new ManagerDAO(em);
        PersonalTest.print(managerDAO.findAll());
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
            PersonalTest.register(new Subordinate(code, name));
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
            PersonalTest.register(supervisor);
            PersonalTest.assignNewSubordinatesToSupervisor(supervisor);
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
            PersonalTest.register(manager);
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
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor registered yet!!!");
            return;
        }
        System.out.println("Enter the info of the new sector below:");
        String name = keyboard.readString("name: ");
        System.out.println("Enter its supervisor:");
        for (int i = 0; i < supervisors.size(); i++) {
            System.out.println(i + ") " + supervisors.get(i));
        }
        System.out.println("-1) quit");
        int option = keyboard.readInteger("Enter an option: ");
        while (option < 0 || option >= supervisors.size()) {
            if (option == -1) {
                System.out.println("Aborting registration ...");
                return;
            }
            System.out.println("Invalid option: " + option + "!!! Try again.");
            option = keyboard.readInteger("Enter an option: ");
        }
        Supervisor supervisor = supervisors.get(option);
        try {
            PersonalTest.register(new Sector(name, supervisor));
            System.out.println("The sector registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The sector registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void assignNewSubordinatesToSupervisor() {
        System.out.println("\nAssigning new subordinates to a supervisor ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor registered yet!!!");
            return;
        }
        System.out.println("Pick one supervisor:");
        for (int i = 0; i < supervisors.size(); i++) {
            System.out.println(i + ") " + supervisors.get(i));
        }
        System.out.println("-1) quit");
        int option = keyboard.readInteger("Enter an option: ");
        while (option < 0 || option >= supervisors.size()) {
            if (option == -1) {
                System.out.println("Aborting registration ...");
                return;
            }
            System.out.println("Invalid option: " + option + "!!! Try again.");
            option = keyboard.readInteger("Enter an option: ");
        }
        Supervisor supervisor = supervisors.get(option);
        assignNewSubordinatesToSupervisor(supervisor);
    }
    
    public static void assignNewSubordinatesToSupervisor(Supervisor supervisor) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        List<Subordinate> subordinates = subordinateDAO.findAll();
        if (subordinates.isEmpty()) {
            System.out.println("There is no subordinate registered yet!!! Try to assign subordinates to this supervisor after new subordinate registrations.");
            return;
        }
        subordinates.removeAll(supervisor.getSubordinates());
        if (subordinates.isEmpty()) {
            System.out.println("There is no subordinate that is not under his(her) supervision!!! Try to assign subordinates to this supervisor after new subordinate registrations.");
            return;
        }
        System.out.println("Enter his(her) subordinates:");
        Keyboard keyboard = Keyboard.getKeyboard();
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        int option = 0;
        while (option != -1 && !subordinates.isEmpty()) {
            for (int i = 0; i < subordinates.size(); i++) {
                System.out.println(i + ") " + subordinates.get(i));
            }
            System.out.println("-1) quit");
            option = keyboard.readInteger("Enter an option: ");
            if (option >= 0 && option < subordinates.size()) {
                Subordinate subordinate = subordinates.get(option);
                System.out.println("Assigning " + subordinate + " to " + supervisor +"'s supervision!!!");
                supervisor.getSubordinates().add(subordinate);
                try {
                    supervisorDAO.update(supervisor);
                    subordinates.remove(subordinate);
                } catch (RuntimeException e) {
                    System.out.println("Exception catched: " + e.getMessage());
                    em.clear();
                }
            }
        }
    }

    private static void assignNewSupervisorsToManager() {
        System.out.println("\nAssigning new supervisors to a manager ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        ManagerDAO managerDAO = new ManagerDAO(em);
        List<Manager> managers = managerDAO.findAll();
        if (managers.isEmpty()) {
            System.out.println("There is no manager registered yet!!!");
            return;
        }
        System.out.println("Pick one manager:");
        for (int i = 0; i < managers.size(); i++) {
            System.out.println(i + ") " + managers.get(i));
        }
        System.out.println("-1) quit");
        int option = keyboard.readInteger("Enter an option: ");
        while (option < 0 || option >= managers.size()) {
            if (option == -1) {
                System.out.println("Aborting registration ...");
                return;
            }
            System.out.println("Invalid option: " + option + "!!! Try again.");
            option = keyboard.readInteger("Enter an option: ");
        }
        Manager manager = managers.get(option);
        assignNewSupervisorsToManager(manager);
    }

    public static void assignNewSupervisorsToManager(Manager manager) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor registered yet!!! Try to assign supervisors to this manager after new supervisor registrations.");
            return;
        }
        supervisors.removeAll(manager.getSupervisors());
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor that is not under his(her) management!!! Try to assign supervisors to this manager after new supervisor registrations.");
            return;
        }
        System.out.println("Enter his(her) supervisors:");
        Keyboard keyboard = Keyboard.getKeyboard();
        ManagerDAO managerDAO = new ManagerDAO(em);
        int option = 0;
        while (option != -1 && !supervisors.isEmpty()) {
            for (int i = 0; i < supervisors.size(); i++) {
                System.out.println(i + ") " + supervisors.get(i));
            }
            System.out.println("-1) quit");
            option = keyboard.readInteger("Enter an option: ");
            if (option >= 0 && option < supervisors.size()) {
                Supervisor supervisor = supervisors.get(option);
                System.out.println("Assigning " + supervisor + " to " + manager +"'s management!!!");
                manager.getSupervisors().add(supervisor);
                try {
                    managerDAO.update(manager);
                    supervisors.remove(supervisor);
                } catch (RuntimeException e) {
                    System.out.println("Exception catched: " + e.getMessage());
                    em.clear();
                }
            }
        }
    }

    private static void upgradeEmployee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void downgradeEmployee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
