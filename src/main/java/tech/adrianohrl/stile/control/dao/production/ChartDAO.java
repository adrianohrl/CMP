package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.Chart;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartDAO extends DAO<Chart, String> {

    public ChartDAO(EntityManager em) {
        super(em, Chart.class);
    }

    @Override
    public boolean isRegistered(Chart entity) {
        return super.find(entity.getName()) != null;
    }
    
}
