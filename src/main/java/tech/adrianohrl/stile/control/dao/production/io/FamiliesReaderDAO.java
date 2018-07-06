package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FamilyDAO;
import tech.adrianohrl.stile.model.production.io.FamiliesReader;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FamiliesReaderDAO extends ReaderDAO<Family, FamilyDAO> {
    
    public FamiliesReaderDAO(EntityManager em) {
        super(em, new FamilyDAO(em));
        super.setReader(new Reader());
    }
    
    private class Reader extends FamiliesReader {
    
        @Override
        protected Collection getCollection(String name) {
            CollectionDAO collectionDAO = new CollectionDAO(em);
            return collectionDAO.find(name);
        }
        
    }
    
}
