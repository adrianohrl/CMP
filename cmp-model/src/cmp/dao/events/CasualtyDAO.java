/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.dao.DAO;
import cmp.model.events.Casualty;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
