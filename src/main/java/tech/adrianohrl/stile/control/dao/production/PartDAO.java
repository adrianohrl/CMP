package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.dao.DAO;
import tech.adrianohrl.stile.model.production.Part;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PartDAO extends DAO<Part, String> {

    public PartDAO(EntityManager em) {
        super(em, Part.class);
    }

    @Override
    public boolean isRegistered(Part entity) {
        return super.find(entity.getName()) != null;
    }
    
}
