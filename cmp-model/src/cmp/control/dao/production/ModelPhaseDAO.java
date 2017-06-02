/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production;

import cmp.control.dao.DAO;
import cmp.model.production.Model;
import cmp.model.production.ModelPhase;
import cmp.model.production.Phase;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author adrianohrl
 */
public class ModelPhaseDAO extends DAO<ModelPhase, Long> {

    public ModelPhaseDAO(EntityManager em) {
        super(em, ModelPhase.class);
    }

    @Override
    public boolean isRegistered(ModelPhase entity) {
        return super.find(entity.getCode()) != null;
    }
    
    public ModelPhase find(String modelName, String phaseName) {
        try {
            return (ModelPhase) em.createQuery("SELECT mp "
                    + "FROM Model m JOIN m.phases mp "
                    + "WHERE m.name = '" + modelName + "' "
                        + "AND mp.phase.name = '" + phaseName + "'").getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public ModelPhase find(Model model, Phase phase) {
        return find(model.getName(), phase.getName());
    }
    
}
