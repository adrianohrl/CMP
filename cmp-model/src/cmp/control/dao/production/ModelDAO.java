/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production;

import cmp.control.dao.DAO;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ModelDAO extends DAO<Model, String> {

    public ModelDAO(EntityManager em) {
        super(em, Model.class);
    }

    @Override
    public boolean isRegistered(Model entity) {
        return super.find(entity.getReference()) != null;
    }
    
    public boolean isModelPhase(String modelName, String phaseName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Model m JOIN m.phases mp "
                + "WHERE m.name = '" + modelName + "' "
                    + "AND mp.phase.name = '" + phaseName + "'").getSingleResult();
        return counter > 0;
    }
    
    public boolean isModelPhase(Model model, Phase phase) {
        return isModelPhase(model.getName(), phase.getName());
    }
    
}
