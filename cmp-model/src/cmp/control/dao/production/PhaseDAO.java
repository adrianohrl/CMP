/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production;

import cmp.control.dao.DAO;
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
        return super.find(entity.getName()) != null;
    }
    
}
