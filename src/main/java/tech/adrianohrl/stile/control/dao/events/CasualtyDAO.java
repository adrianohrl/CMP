package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.events.Casualty;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CasualtyDAO extends DAO<Casualty, String> {

    public CasualtyDAO(EntityManager em) {
        super(em, Casualty.class);
    }

    @Override
    public boolean isRegistered(Casualty entity) {
        return super.find(entity.getName()) != null;
    }
    
    public List<Casualty> findCollectives() {
        return em.createQuery("SELECT c FROM Casualty c WHERE c.collective = TRUE").getResultList();
    }
    
    public List<Casualty> findNonCollectives() {
        return em.createQuery("SELECT c FROM Casualty c WHERE c.collective = FALSE").getResultList();
    }
    
}
