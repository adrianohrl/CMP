package tech.adrianohrl.util;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.exceptions.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 */
public abstract class AbstractReaderDAO<E extends Comparable, T, D extends DAO<E, T>> extends AbstractReader<E> {
    
    private final EntityManager em;
    private final List<E> registeredEntities = new ArrayList<>();

    protected AbstractReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        for (E entity : getReadEntities()) {
            if (!isRegistered(entity)) {
                register(entity);
                registeredEntities.add(entity);
            }
        }
    }
    
    protected abstract boolean isRegistered(E entity);
    
    protected abstract void register(E entity);
    
    public List<E> getRegisteredEntities() {
        return registeredEntities;
    }

    @Override
    public Iterator<E> iterator() {
        return registeredEntities.iterator();
    }
    
}
