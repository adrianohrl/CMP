/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.dao.DAO;
import cmp.model.production.PhaseProductionOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PhaseProductionOrderDAO extends DAO<PhaseProductionOrder, Long> {

    public PhaseProductionOrderDAO(EntityManager em) {
        super(em, PhaseProductionOrder.class);
    }

    @Override
    public boolean isRegistered(PhaseProductionOrder entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
