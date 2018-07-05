package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.model.production.io.CollectionsReader;
import tech.adrianohrl.stile.model.production.Collection;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CollectionsReaderDAO extends CollectionsReader {
    
    private final EntityManager em;
    private final List<Collection> registeredCollections = new ArrayList<>();

    public CollectionsReaderDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
        CollectionDAO collectionDAO = new CollectionDAO(em);
        for (Collection collection : getReadEntities()) {
            if (!collectionDAO.isRegistered(collection)) {
                collectionDAO.create(collection);
                registeredCollections.add(collection);
            }
        }
    }

    public List<Collection> getRegisteredCollections() {
        return registeredCollections;
    }

    @Override
    public Iterator<Collection> iterator() {
        return registeredCollections.iterator();
    }
    
}
