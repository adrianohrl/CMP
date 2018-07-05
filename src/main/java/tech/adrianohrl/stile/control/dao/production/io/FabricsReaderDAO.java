package tech.adrianohrl.stile.control.dao.production.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FabricDAO;
import tech.adrianohrl.stile.control.model.production.io.FabricsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FabricsReaderDAO extends FabricsReader {
    
    private final EntityManager em;
    private final List<Fabric> registeredFabrics = new ArrayList<>();

    public FabricsReaderDAO(EntityManager em) {
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
        FabricDAO fabricDAO = new FabricDAO(em);
        for (Fabric fabric : getReadEntities()) {
            if (!fabricDAO.isRegistered(fabric)) {
                fabricDAO.create(fabric);
                registeredFabrics.add(fabric);
            }
        }
    }

    @Override
    protected Collection getCollection(String name) {
        CollectionDAO collectionDAO = new CollectionDAO(em);
        return collectionDAO.find(name);
    }

    public List<Fabric> getRegisteredFabrics() {
        return registeredFabrics;
    }

    @Override
    public Iterator<Fabric> iterator() {
        return registeredFabrics.iterator();
    }
    
}
