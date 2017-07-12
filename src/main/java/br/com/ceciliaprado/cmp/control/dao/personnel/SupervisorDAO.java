/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel;

import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.List;
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
                + "FROM Supervisor s "
                    + "JOIN s.subordinates sub "
                + "WHERE s.name = '" + supervisorName + "' "
                    + "AND sub.name = '" + subordinateName + "'").getSingleResult();
        return counter > 0;
    }
    
    public boolean isSupervisorSubordinate(Supervisor supervisor, Subordinate subordinate) {
        return isSupervisorSubordinate(supervisor.getName(), subordinate.getName());
    }
    
    public List<Sector> findSupervisorSectors(String supervisorName) {
        return em.createQuery("SELECT sec "
                + "FROM Sector sec "
                + "WHERE sec.supervisor.name = '" + supervisorName + "'").getResultList();
    }
    
    public List<Sector> findSupervisorSectors(Supervisor supervisor) {
        return findSupervisorSectors(supervisor.getName());
    }
    
}
