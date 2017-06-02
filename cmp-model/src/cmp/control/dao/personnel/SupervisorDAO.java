/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personnel;

import cmp.model.personnel.Supervisor;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SupervisorDAO extends LoggableDAO<Supervisor> {

    public SupervisorDAO(EntityManager em) {
        super(em, Supervisor.class);
    }
    
    public boolean isSupervisorSubordinate(String supervisorName, String subordinateName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Supervisor s JOIN s.subordinates sub "
                + "WHERE s.name = '" + supervisorName + "' "
                + "AND sub.name = '" + subordinateName + "'").getSingleResult();
        return counter > 0;
    }
    
}
