/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personal;

import cmp.model.personal.Supervisor;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SupervisorDAO extends LoggableDAO<Supervisor> {

    public SupervisorDAO(EntityManager em) {
        super(em, Supervisor.class);
    }
    
}
