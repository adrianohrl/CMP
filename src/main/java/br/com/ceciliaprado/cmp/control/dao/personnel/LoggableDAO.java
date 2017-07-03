/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel;

import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
public class LoggableDAO<L extends Loggable> extends EmployeeDAO<L> {

    public LoggableDAO(EntityManager em) {
        super(em);
    }
    
    protected LoggableDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }
    
}
