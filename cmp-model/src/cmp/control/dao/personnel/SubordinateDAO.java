/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personnel;

import cmp.model.personnel.Subordinate;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SubordinateDAO extends EmployeeDAO<Subordinate> {

    public SubordinateDAO(EntityManager em) {
        super(em, Subordinate.class);
    }
    
    public List<Subordinate> findAllAvailable() {
        return em.createQuery("SELECT s "
                + "FROM Subordinate s "
                + "WHERE s.available = true").getResultList();
    }
    
}
