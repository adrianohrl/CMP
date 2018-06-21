/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.dao;

import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <A>
 * @param <K>
 */
public abstract class ArchivableDAO<A extends Archivable, K> extends DAO<A, K> {

    protected ArchivableDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }
    
    public List<A> findAllNonArchived() {
        return em.createQuery("FROM " + clazz.getSimpleName() + " e "
                + "WHERE e.archived ").getResultList();
    }
    
    public List<A> findAllArchived() {
        return em.createQuery("FROM " + clazz.getSimpleName() + " e "
                + "WHERE NOT e.archived ").getResultList();
    }
    
}
