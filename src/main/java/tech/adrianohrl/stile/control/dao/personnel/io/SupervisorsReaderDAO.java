package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.SupervisorsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Manager;
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
public class SupervisorsReaderDAO extends SupervisorsReader {
    
    private final EntityManager em;
    private final List<Manager> registeredManagers = new ArrayList<>();

    public SupervisorsReaderDAO(EntityManager em) {
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
        ManagerDAO managerDAO = new ManagerDAO(em);
        for (Manager manager : getReadEntities()) {
            managerDAO.update(manager);
            registeredManagers.add(manager);
        }
    }

    @Override
    protected Supervisor getSupervisor(String supervisorName) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.find(supervisorName);
    }

    @Override
    protected Manager getManager(String managerName) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        return managerDAO.find(managerName);
    }

    public List<Manager> getRegisteredManagers() {
        return registeredManagers;
    }
    
    @Override
    public Iterator<Manager> iterator() {
        return registeredManagers.iterator();
    }
    
}
