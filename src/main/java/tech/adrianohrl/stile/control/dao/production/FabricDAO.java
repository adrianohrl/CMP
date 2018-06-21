package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.model.production.Fabric;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FabricDAO extends ArchivableDAO<Fabric, String> {

    public FabricDAO(EntityManager em) {
        super(em, Fabric.class);
    }

    @Override
    public boolean isRegistered(Fabric entity) {
        return super.find(entity.getName()) != null;
    }
    
}
