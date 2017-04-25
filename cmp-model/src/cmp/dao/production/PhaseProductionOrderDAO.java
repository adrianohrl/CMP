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
        return super.find(entity.getCode()) != null;
    }
    
    public PhaseProductionOrder find(String phaseName, String productionOrderName) {
        return (PhaseProductionOrder) em.createQuery("SELECT ppo FROM PhaseProductionOrder ppo WHERE ppo.phase.name = '" + phaseName + "' AND ppo.productionOrder.productionOrder = '" + productionOrderName + "'").getSingleResult();
    }
    
}
