package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.io.SectorsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SectorsReaderDAO extends ReaderDAO<Sector, SectorDAO> {
    
    public SectorsReaderDAO(EntityManager em) {
        super(em, new SectorDAO(em));
        super.setReader(new Reader());
    }
    
    private class Reader extends SectorsReader {
    
        @Override
        protected Supervisor getSupervisor(String name) throws IOException {
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            return supervisorDAO.find(name);
        }
        
    }
    
}
