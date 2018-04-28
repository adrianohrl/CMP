/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.util;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.exceptions.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
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
