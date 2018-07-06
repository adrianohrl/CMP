package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.model.production.io.PhasesReader;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.production.Phase;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PhasesReaderDAO extends ReaderDAO<Phase, PhaseDAO> {
    
    public PhasesReaderDAO(EntityManager em) {
        super(em, new PhaseDAO(em));
        super.setReader(new Reader());
    }
    
    private class Reader extends PhasesReader {
    
        @Override
        protected Sector getSector(String sectorName) {
            SectorDAO sectorDAO = new SectorDAO(em);
            return sectorDAO.find(sectorName);
        }
        
    }
    
}
