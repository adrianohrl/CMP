package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.model.production.Variant;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class VariantDAO extends ArchivableDAO<Variant, String> {

    public VariantDAO(EntityManager em) {
        super(em, Variant.class);
    }

    @Override
    public boolean isRegistered(Variant entity) {
        return super.find(entity.getName()) != null;
    }
    
}
