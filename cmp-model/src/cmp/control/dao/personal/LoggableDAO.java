/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personal;

import cmp.model.personal.Loggable;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
public abstract class LoggableDAO<L extends Loggable> extends EmployeeDAO<L> {

    protected LoggableDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }
    
}
