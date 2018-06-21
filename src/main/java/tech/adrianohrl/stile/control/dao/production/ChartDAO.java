package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.model.production.Chart;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartDAO extends ArchivableDAO<Chart, String> {

    public ChartDAO(EntityManager em) {
        super(em, Chart.class);
    }

    @Override
    public boolean isRegistered(Chart entity) {
        return super.find(entity.getName()) != null;
    }
    
}
