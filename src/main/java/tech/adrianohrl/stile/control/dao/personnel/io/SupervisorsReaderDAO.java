package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.SupervisorsReader;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SupervisorsReaderDAO extends ReaderDAO<Manager, ManagerDAO> {
    
    private final SupervisorsReader supervisorsReader;

    public SupervisorsReaderDAO(EntityManager em) {
        super(em, new ManagerDAO(em));
        this.supervisorsReader = new Reader();
        super.setReader(supervisorsReader);
    }
    
    @Override
    protected void register() {
        ManagerDAO managerDAO = new ManagerDAO(em);
        for (Manager manager : supervisorsReader) {
            managerDAO.update(manager);
            registeredEntities.add(manager);
        }
    }
    
    private class Reader extends SupervisorsReader {

        @Override
        protected Supervisor getSupervisor(String name) {
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            return supervisorDAO.find(name);
        }

        @Override
        protected Manager getManager(String name) {
            ManagerDAO managerDAO = new ManagerDAO(em);
            return managerDAO.find(name);
        }

    } 
    
}
