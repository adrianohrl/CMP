package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.model.production.io.ModelsReader;
import tech.adrianohrl.stile.model.production.Model;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FamilyDAO;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelsReaderDAO extends ReaderDAO<Model, ModelDAO> {
    
    public ModelsReaderDAO(EntityManager em) {
        super(em, new ModelDAO(em));
        super.setReader(new Reader());
    }
    
    private class Reader extends ModelsReader {
    
        @Override
        protected Family getFamily(String name) {
            FamilyDAO familyDAO = new FamilyDAO(em);
            return familyDAO.find(name);
        }

        @Override
        protected Collection getCollection(String name) {
            CollectionDAO collectionDAO = new CollectionDAO(em);
            return collectionDAO.find(name);
        }

        @Override
        protected Chart getChart(String name) {
            ChartDAO chartDAO = new ChartDAO(em);
            return chartDAO.find(name);
        }
        
    }
    
}
