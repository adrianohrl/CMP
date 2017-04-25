/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.dao.DAO;
import cmp.model.production.Model;
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
    
}
