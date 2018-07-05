package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.EmployeeDAO;
import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.PersonnelReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PersonnelReaderDAO extends PersonnelReader {
    
    private final EntityManager em;
    private final List<Employee> registeredEmployees = new ArrayList<>();

    public PersonnelReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        register();
    }
    
    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        for (Employee employee : getReadEntities()) {
            if (!employeeDAO.isRegistered(employee)) {
                if (employee instanceof Subordinate) {
                    register((Subordinate) employee);
                } else if (employee instanceof Supervisor) {
                    register((Supervisor) employee);
                } else {
                    register((Manager) employee);
                }
                registeredEmployees.add(employee);
            }
        }
    }
    
    private void register(Subordinate subordinate) {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinateDAO.create(subordinate);
    }
    
    private void register(Supervisor supervisor) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisorDAO.create(supervisor);
    }
    
    private void register(Manager manager) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        managerDAO.create(manager);
    }

    public List<Employee> getRegisteredEmployees() {
        return registeredEmployees;
    }

    @Override
    public Iterator<Employee> iterator() {
        return registeredEmployees.iterator();
    }
    
}
