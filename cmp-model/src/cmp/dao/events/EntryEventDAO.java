/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.model.events.EntryEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public class EntryEventDAO<E extends EntryEvent> extends AbstractEmployeeRelatedEventDAO<E> {

    public EntryEventDAO(EntityManager em) {
        super(em, EntryEvent.class);
    }
    
    protected EntryEventDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }
    
}
