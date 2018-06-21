package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.model.production.Family;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FamilyDAO extends ArchivableDAO<Family, String> {

    public FamilyDAO(EntityManager em) {
        super(em, Family.class);
    }

    @Override
    public boolean isRegistered(Family entity) {
        return super.find(entity.getName()) != null;
    }
    
}
