package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.personnel.Sector;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SectorDAO extends DAO<Sector, String> {

    public SectorDAO(EntityManager em) {
        super(em, Sector.class);
    }

    @Override
    public boolean isRegistered(Sector entity) {
        return super.find(entity.getName()) != null;
    }
    
    public boolean isSectorSupervisor(String sectorName, String supervisorName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Sector s "
                    + "JOIN s.supervisor sup "
                + "WHERE s.name = '" + sectorName + "' "
                    + "AND sup.name = '" + supervisorName + "'").getSingleResult();
        return counter > 0;
    }
    
}
