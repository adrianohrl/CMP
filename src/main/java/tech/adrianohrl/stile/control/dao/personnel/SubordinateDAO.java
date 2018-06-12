package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SubordinateDAO extends EmployeeDAO<Subordinate> {

    public SubordinateDAO(EntityManager em) {
        super(em, Subordinate.class);
    }
    
    public boolean isAvailable(String subordinateName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Subordinate s "
                + "WHERE s.name = '" + subordinateName + "' "
                    + "AND s.available = TRUE").getSingleResult();
        return counter > 0;
    }
    
    public boolean isAvailable(Subordinate subordinate) {
        return isAvailable(subordinate.getName());
    }
    
    public List<Subordinate> findAllAvailable() {
        return em.createQuery("SELECT s "
                + "FROM Subordinate s "
                + "WHERE s.available = TRUE").getResultList();
    }
    
    public boolean isWorkingAtSector(String subordinateName, String sectorName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM PhaseProductionOrder ppo "
                + "WHERE ppo.pendent = TRUE "
                    + "AND ppo.subordinate.name = '" + subordinateName + "' "
                    + "AND ppo.phase.phase.sector.name = '" + sectorName + "'").getSingleResult();
        return counter > 0;
    }
    
    public boolean isWorkingAtSector(Subordinate subordinate, Sector sector) {
        return isWorkingAtSector(subordinate.getName(), sector.getName());
    }
    
    public List<Subordinate> findSupervisorAndSectorSubordinates(String supervisorName, String sectorName) {
        return em.createQuery("SELECT sub "
                + "FROM Sector sec "
                    + "JOIN sec.supervisor.subordinates sub, "
                    + "PhaseProductionOrder ppo "
                + "WHERE sec.name = '" + sectorName + "' "  
                    + "AND sec.supervisor.name = '" + supervisorName + "' "
                    + "AND ("
                        + "sub.available = TRUE "
                        + " OR ("
                            + "ppo.pendent = TRUE "
                            + "AND ppo.subordinate.name = sub.name "                            
                            + "AND ppo.phase.phase.sector.name = '" + sectorName + "' "
                            + ")"
                    + ") "
                + "GROUP BY sub").getResultList();
    }
    
    public List<Subordinate> findSupervisorAndSectorSubordinates(Supervisor supervisor, Sector sector) {
        return findSupervisorAndSectorSubordinates(supervisor.getName(), sector.getName());
    }
    
}
