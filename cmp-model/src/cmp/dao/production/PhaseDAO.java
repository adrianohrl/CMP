/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.dao.DAO;
import cmp.model.production.Phase;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PhaseDAO extends DAO<Phase, String> {

    public PhaseDAO(EntityManager em) {
        super(em, Phase.class);
    }

    @Override
    public boolean isRegistered(Phase entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
