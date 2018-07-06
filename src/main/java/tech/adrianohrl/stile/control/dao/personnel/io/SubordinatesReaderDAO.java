package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.SubordinatesReader;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SubordinatesReaderDAO extends ReaderDAO<Supervisor, SupervisorDAO> {
    
    private final SubordinatesReader subordinatesReader;

    public SubordinatesReaderDAO(EntityManager em) {
        super(em, new SupervisorDAO(em));
        this.subordinatesReader = new Reader();
        super.setReader(subordinatesReader);
    }
    
    @Override
    protected void register() {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        for (Supervisor supervisor : subordinatesReader) {
            supervisorDAO.update(supervisor);
            registeredEntities.add(supervisor);
        }
    }
    
    private class Reader extends SubordinatesReader {
        
        @Override
        protected Subordinate getSubordinate(String name) {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            return subordinateDAO.find(name);
        }

        @Override
        protected Supervisor getSupervisor(String name) {
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            return supervisorDAO.find(name);
        }

    }
    
}
