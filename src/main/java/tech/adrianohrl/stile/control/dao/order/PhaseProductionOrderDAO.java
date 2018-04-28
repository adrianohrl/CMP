/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.order;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import java.util.List;
import javax.persistence.EntityExistsException;
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
    public void create(PhaseProductionOrder phaseProductionOrde) throws EntityExistsException {
        Subordinate subordinate = phaseProductionOrde.getSubordinate();
        if (subordinate != null) {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinateDAO.update(subordinate);
        }
        super.create(phaseProductionOrde);
    }

    @Override
    public void update(PhaseProductionOrder phaseProductionOrde) {
        Subordinate subordinate = phaseProductionOrde.getSubordinate();
        if (subordinate != null) {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinateDAO.update(subordinate);
        }
        super.update(phaseProductionOrde);
    }

    @Override
    public boolean isRegistered(PhaseProductionOrder phaseProductionOrde) {
        return super.find(phaseProductionOrde.getCode()) != null;
    }
    
    public PhaseProductionOrder find(String phaseName, String productionOrderName) {
        try {
            return (PhaseProductionOrder) em.createQuery("SELECT ppo "
                    + "FROM PhaseProductionOrder ppo "
                    + "WHERE ppo.phase.phase.name = '" + phaseName + "' "
                        + "AND ppo.productionOrder.reference = '" + productionOrderName + "'").getSingleResult();
        } catch (NoResultException e) {
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
                    + "JOIN ppo.productionOrder.model.phases mp "
                + "WHERE ppo.pendent = TRUE "
                    + "AND mp.phase.sector.name = '" + sector.getName() + "'").getResultList();
    }
    
    public List<PhaseProductionOrder> findPendents(ProductionOrder productionOrder) {
        return em.createQuery("SELECT ppo "
                + "FROM PhaseProductionOrder ppo "
                + "WHERE ppo.pendent = TRUE "
                    + "AND ppo.productionOrder.reference = '" + productionOrder.getReference() + "'").getResultList();
    }
    
    public PhaseProductionOrder findCurrent(String subordinateName) {
        try {
            return (PhaseProductionOrder) em.createQuery("SELECT ee.phaseProductionOrder "
                + "FROM EntryEvent ee "
                + "WHERE ee.eventDate in ("
                    + "SELECT MAX(e.eventDate) "
                    + "FROM EntryEvent e JOIN e.phaseProductionOrder ppo "
                    + "WHERE ppo.pendent = TRUE "
                        + "AND ppo.subordinate.name = '" + subordinateName + "'"
                + ")").getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public PhaseProductionOrder findCurrent(Subordinate subordinate) {
        return findCurrent(subordinate.getName());
    }
    
}
