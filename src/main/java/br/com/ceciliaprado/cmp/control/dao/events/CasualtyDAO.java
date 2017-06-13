/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class CasualtyDAO extends DAO<Casualty, String> {

    public CasualtyDAO(EntityManager em) {
        super(em, Casualty.class);
    }

    @Override
    public boolean isRegistered(Casualty entity) {
        return super.find(entity.getName()) != null;
    }
    
    public List<Casualty> findCollectives() {
        return em.createQuery("SELECT c FROM Casualty c WHERE c.collective = TRUE").getResultList();
    }
    
    public List<Casualty> findNonCollectives() {
        return em.createQuery("SELECT c FROM Casualty c WHERE c.collective = FALSE").getResultList();
    }
    
}
