package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.EmployeeDAO;
import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.PersonnelReader;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PersonnelReaderDAO extends ReaderDAO<Employee, EmployeeDAO<Employee>> {
    
    private final PersonnelReader personnelReader;
    
    public PersonnelReaderDAO(EntityManager em) {
        super(em, new EmployeeDAO<>(em));
        this.personnelReader = new PersonnelReader();
        super.setReader(personnelReader);
    }
    
    @Override
    protected void register() {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        for (Employee employee : personnelReader) {
            if (!employeeDAO.isRegistered(employee)) {
                if (employee instanceof Subordinate) {
                    register((Subordinate) employee);
                } else if (employee instanceof Supervisor) {
                    register((Supervisor) employee);
                } else {
                    register((Manager) employee);
                }
                registeredEntities.add(employee);
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
    
}
