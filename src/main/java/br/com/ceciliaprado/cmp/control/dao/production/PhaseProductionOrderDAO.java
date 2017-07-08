/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
        try {
            return (PhaseProductionOrder) em.createQuery("SELECT ppo "
                    + "FROM PhaseProductionOrder ppo "
                    + "WHERE ppo.phase.phase.name = '" + phaseName + "' "
                        + "AND ppo.productionOrder.reference = '" + productionOrderName + "'").getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public PhaseProductionOrder find(Phase phase, ProductionOrder productionOrder) {
        return find(phase.getName(), productionOrder.getReference());
    }
    
    public PhaseProductionOrder find(ModelPhase phase, ProductionOrder productionOrder) {
        return find(phase.getPhase().getName(), productionOrder.getReference());
    }
    
    public List<PhaseProductionOrder> findPendents() {
        return em.createQuery("SELECT ppo "
                + "FROM PhaseProductionOrder ppo "
                + "WHERE ppo.pendent = TRUE").getResultList();
    }
    
    public List<PhaseProductionOrder> findPendents(Sector sector) {
        return em.createQuery("SELECT ppo "
                + "FROM PhaseProductionOrder ppo "
                    + "JOIN ppo.productionOrder.model.phases mp"
                + "WHERE ppo.pendent = TRUE "
                    + "AND mp.phase.sector.name = '" + sector.getName() + "'").getResultList();
    }
    
}
