/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production;

import cmp.control.dao.DAO;
import cmp.model.personnel.Sector;
import cmp.model.production.Phase;
import java.util.List;
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
    
    public List<Phase> findAll(String sectorName) {
        return em.createQuery("SELECT p "
                + "FROM Phase p "
                + "WHERE p.sector.name = '" + sectorName + "'").getResultList();
    }
    
    public boolean isSectorPhase(String sectorName, String phaseName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Phase p "
                + "WHERE p.sector.name = '" + sectorName + "' "
                    + "AND p.name = '" + phaseName + "'").getSingleResult();
        return counter > 0;
    }
    
    public boolean isSectorPhase(Sector sector, Phase phase) {
        return isSectorPhase(sector.getName(), phase.getName());
    }
    
}
