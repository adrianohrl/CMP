/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personnel;

import cmp.control.dao.personnel.ManagerDAO;
import cmp.control.dao.personnel.SupervisorDAO;
import cmp.control.dao.personnel.EmployeeDAO;
import cmp.control.dao.personnel.SubordinateDAO;
import cmp.control.dao.personnel.SectorDAO;
import cmp.control.dao.DataSource;
import cmp.model.personnel.Employee;
import cmp.model.personnel.Manager;
import cmp.model.personnel.Sector;
import cmp.model.personnel.Subordinate;
import cmp.model.personnel.Supervisor;
import cmp.util.KeyboardEntries;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PersonnelKeyboardEntries {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static Employee selectOneEmployee() {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        List<Employee> employees = employeeDAO.findAll();
        return KeyboardEntries.selectOne(employees, "employee");
    }
    
    public static Subordinate selectOneSubordinate() {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        List<Subordinate> subordinates = subordinateDAO.findAll();
        return KeyboardEntries.selectOne(subordinates, "subordinate");
    }
    
    public static Subordinate selectOneSubordinate(Supervisor supervisor) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        List<Subordinate> subordinates = subordinateDAO.findAll();
        if (subordinates.isEmpty()) {
            System.out.println("There is no subordinate registered yet!!!");
            return null;
        }
        subordinates.removeAll(supervisor.getSubordinates());
        return KeyboardEntries.selectOne(subordinates, "subordinate that is not under to this supervisor");
    }
    
    public static Subordinate selectOneSubordinateOfSupervisor(String supervisorName) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        Supervisor supervisor = supervisorDAO.find(supervisorName);
        if (supervisor == null) {
            System.out.println("There is no supervisor named " + supervisorName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectOne(supervisor.getSubordinates(), "subordinate of this supervisor");
    }
    
    public static Supervisor selectOneSupervisor() {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        return KeyboardEntries.selectOne(supervisors, "supervisor");
    }
    
    public static Supervisor selectOneSupervisor(Manager manager) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor registered yet!!!");
            return null;
        }
        supervisors.removeAll(manager.getSupervisors());
        return KeyboardEntries.selectOne(supervisors, "supervisor that is not under to this manager");
    }
    
    public static Supervisor selectOneSupervisorOfManager(String managerName) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        Manager manager = managerDAO.find(managerName);
        if (manager == null) {
            System.out.println("There is no manager named " + managerName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectOne(manager.getSupervisors(), "supervisor of this manager");
    }
    
    public static Manager selectOneManager() {
        ManagerDAO managerDAO = new ManagerDAO(em);
        List<Manager> managers = managerDAO.findAll();
        return KeyboardEntries.selectOne(managers, "manager");
    }
    
    public static Sector selectOneSector() {
        SectorDAO sectorDAO = new SectorDAO(em);
        List<Sector> sectors = sectorDAO.findAll();
        return KeyboardEntries.selectOne(sectors, "sector");
    }
    
    public static Sector selectOneSector(Supervisor supervisor) {
        SectorDAO sectorDAO = new SectorDAO(em);
        List<Sector> sectors = sectorDAO.findAll();
        Iterator<Sector> it = sectors.iterator();
        while (it.hasNext()) {
            Sector sector = it.next();
            if (!sector.isSupervisedBy(supervisor)) {
                it.remove();
            }
        }
        return KeyboardEntries.selectOne(sectors, "sector supervised by this supervisor");
    }
    
    public static List<Employee> selectManyEmployees() {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        List<Employee> employees = employeeDAO.findAll();
        return KeyboardEntries.selectMany(employees, "employee");
    }
    
    public static List<Subordinate> selectManySubordinates() {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        List<Subordinate> subordinates = subordinateDAO.findAll();
        return KeyboardEntries.selectMany(subordinates, "subordinate");
    }
    
    public static List<Subordinate> selectManySubordinates(Supervisor supervisor) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        List<Subordinate> subordinates = subordinateDAO.findAll();
        if (subordinates.isEmpty()) {
            System.out.println("There is no subordinate registered yet!!!");
            return null;
        }
        subordinates.removeAll(supervisor.getSubordinates());
        return KeyboardEntries.selectMany(subordinates, "subordinate that is not under to this supervisor");
    }
    
    public static List<Subordinate> selectManySubordinatesOfSupervisor(String supervisorName) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        Supervisor supervisor = supervisorDAO.find(supervisorName);
        if (supervisor == null) {
            System.out.println("There is no supervisor named " + supervisorName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectMany(supervisor.getSubordinates(), "subordinate of this supervisor");
    }
    
    public static List<Supervisor> selectManySupervisors() {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        return KeyboardEntries.selectMany(supervisors, "supervisor");
    }
    
    public static  List<Supervisor> selectManySupervisors(Manager manager) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        if (supervisors.isEmpty()) {
            System.out.println("There is no supervisor registered yet!!!");
            return null;
        }
        supervisors.removeAll(manager.getSupervisors());
        return KeyboardEntries.selectMany(supervisors, "supervisor that is not under to this manager");
    }
    
    public static  List<Supervisor> selectManySupervisorsOfManager(String managerName) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        Manager manager = managerDAO.find(managerName);
        if (manager == null) {
            System.out.println("There is no manager named " + managerName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectMany(manager.getSupervisors(), "supervisor of this manager");
    }
    
    public static List<Manager> selectManyManagers() {
        ManagerDAO managerDAO = new ManagerDAO(em);
        List<Manager> managers = managerDAO.findAll();
        return KeyboardEntries.selectMany(managers, "manager");
    }
    
    public static List<Sector> selectManySectors() {
        SectorDAO sectorDAO = new SectorDAO(em);
        List<Sector> sectors = sectorDAO.findAll();
        return KeyboardEntries.selectMany(sectors, "sector");
    }
    
}
