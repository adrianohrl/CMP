package tech.adrianohrl.stile.control.dao.production.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FamilyDAO;
import tech.adrianohrl.stile.model.production.io.FamiliesReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FamiliesReaderDAO extends FamiliesReader {
    
    private final EntityManager em;
    private final List<Family> registeredFamilies = new ArrayList<>();

    public FamiliesReaderDAO(EntityManager em) {
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
        FamilyDAO familyDAO = new FamilyDAO(em);
        for (Family family : getReadEntities()) {
            if (!familyDAO.isRegistered(family)) {
                familyDAO.create(family);
                registeredFamilies.add(family);
            }
        }
    }

    @Override
    protected Collection getCollection(String name) {
        CollectionDAO collectionDAO = new CollectionDAO(em);
        return collectionDAO.find(name);
    }

    public List<Family> getRegisteredFamilies() {
        return registeredFamilies;
    }

    @Override
    public Iterator<Family> iterator() {
        return registeredFamilies.iterator();
    }
    
}
