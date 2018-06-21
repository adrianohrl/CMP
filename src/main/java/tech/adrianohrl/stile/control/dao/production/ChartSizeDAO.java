package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.dao.DAO;
import tech.adrianohrl.stile.model.production.ChartSize;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartSizeDAO extends DAO<ChartSize, String> {

    public ChartSizeDAO(EntityManager em) {
        super(em, ChartSize.class);
    }

    @Override
    public boolean isRegistered(ChartSize entity) {
        return super.find(entity.getName()) != null;
    }
    
}
