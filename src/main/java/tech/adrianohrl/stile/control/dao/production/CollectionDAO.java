package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.model.production.Collection;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CollectionDAO extends ArchivableDAO<Collection, String> {

    public CollectionDAO(EntityManager em) {
        super(em, Collection.class);
    }

    @Override
    public boolean isRegistered(Collection entity) {
        return super.find(entity.getName()) != null;
    }
    
}
