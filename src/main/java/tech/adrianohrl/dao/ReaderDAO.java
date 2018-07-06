package tech.adrianohrl.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.util.AbstractReader;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 * @param <D>
 */
public abstract class ReaderDAO<E extends Comparable, D extends DAO<E, ?>> implements Iterable<E> {
    
    protected final EntityManager em;
    protected final List<E> registeredEntities = new ArrayList<>();
    protected final DAO<E, ?> dao;
    private AbstractReader<E> reader = null;

    public ReaderDAO(EntityManager em, DAO<E, ?> dao) {
        this.em = em;
        this.dao = dao;
    }
    
    protected void setReader(AbstractReader<E> reader) {
        this.reader = reader;
    }

    public void readFile(String fileName) throws IOException {
        if (reader == null) {
            throw new IOException("The abstract reader must not be null!!!");
        }
        reader.readFile(fileName);
        register();
    }

    public void readFile(InputStream in) throws IOException {
        if (reader == null) {
            throw new IOException("The abstract reader must not be null!!!");
        }
        reader.readFile(in);
        register();
    }
    
    protected void register() {
        for (E entity : reader) {
            if (!dao.isRegistered(entity)) {
                dao.create(entity);
                registeredEntities.add(entity);
            }
        }
    }

    public List<E> getRegisteredEntities() {
        return registeredEntities;
    }

    @Override
    public Iterator<E> iterator() {
        return registeredEntities.iterator();
    }
    
}
