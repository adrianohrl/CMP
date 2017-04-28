/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.dao.DAO;
import cmp.model.events.AbstractEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public class AbstractEventDAO<E extends AbstractEvent> extends DAO<E, Long> {

    protected AbstractEventDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }

    @Override
    public boolean isRegistered(E entity) {
        return super.find(entity.getCode()) != null;
    }
    
}
