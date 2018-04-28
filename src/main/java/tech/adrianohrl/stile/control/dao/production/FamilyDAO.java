/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.Family;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class FamilyDAO extends DAO<Family, String> {

    public FamilyDAO(EntityManager em) {
        super(em, Family.class);
    }

    @Override
    public boolean isRegistered(Family entity) {
        return super.find(entity.getName()) != null;
    }
    
}
