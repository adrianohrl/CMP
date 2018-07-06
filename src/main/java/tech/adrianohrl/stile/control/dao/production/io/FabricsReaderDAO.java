package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FabricDAO;
import tech.adrianohrl.stile.model.production.io.FabricsReader;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FabricsReaderDAO extends ReaderDAO<Fabric, FabricDAO> {
    
    public FabricsReaderDAO(EntityManager em) {
        super(em, new FabricDAO(em));
        super.setReader(new Reader());
    }
    
    private class Reader extends FabricsReader {
    
        @Override
        protected Collection getCollection(String name) {
            CollectionDAO collectionDAO = new CollectionDAO(em);
            return collectionDAO.find(name);
        }
        
    }
    
}
