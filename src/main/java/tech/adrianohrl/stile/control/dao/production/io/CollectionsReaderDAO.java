package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.model.production.io.CollectionsReader;
import tech.adrianohrl.stile.model.production.Collection;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CollectionsReaderDAO  extends ReaderDAO<Collection, CollectionDAO> {
    
    public CollectionsReaderDAO(EntityManager em) {
        super(em, new CollectionDAO(em));
        super.setReader(new CollectionsReader());
    } 
    
}
