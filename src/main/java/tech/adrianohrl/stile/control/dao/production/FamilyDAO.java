package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.Family;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FamilyDAO extends DAO<Family, String> {

    public FamilyDAO(EntityManager em) {
        super(em, Family.class);
    }

    @Override
    public boolean isRegistered(Family entity) {
        return super.find(entity.getName()) != null;
    }
    
}
