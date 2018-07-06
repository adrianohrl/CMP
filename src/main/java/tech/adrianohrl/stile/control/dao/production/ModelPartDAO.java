package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.dao.DAO;
import tech.adrianohrl.stile.model.production.ModelPart;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelPartDAO extends DAO<ModelPart, String> {

    public ModelPartDAO(EntityManager em) {
        super(em, ModelPart.class);
    }

    @Override
    public boolean isRegistered(ModelPart entity) {
        return super.find(entity.getName()) != null;
    }
    
}
