/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.model.events.AbstractEmployeeRelatedEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public class AbstractEmployeeRelatedEventDAO<E extends AbstractEmployeeRelatedEvent> extends AbstractEventDAO<E> {

    protected AbstractEmployeeRelatedEventDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }
    
}
