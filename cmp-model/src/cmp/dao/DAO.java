/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author adrianohrl
 * @param <E>
 * @param <K>
 */
public abstract class DAO<E, K> {
    
    protected EntityManager em;
    protected final Class<E> clazz;
    
    protected DAO(EntityManager em, Class clazz) {
        this.em = em;
        this.clazz = (Class<E>) clazz;
    }

    public void create(E entity) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new EntityExistsException(e);
        }
    }

    public void update(E entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public E find(K key) {
        return em.find(clazz, key);
    }

    public void remove(E entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    public void removeByKey(K key) {
        E entity = find(key);
        remove(entity);
    }

    public List<E> findAll() {
        return em.createQuery("FROM " + clazz.getSimpleName()).getResultList();
    }

    public long getCount() {
        return (long) em.createQuery("SELECT COUNT(*) FROM " + clazz.getSimpleName()).getSingleResult();
    }
    
    public abstract boolean isRegistered(E entity);
    
}
