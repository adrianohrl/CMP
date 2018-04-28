/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.Collection;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class CollectionDAO extends DAO<Collection, String> {

    public CollectionDAO(EntityManager em) {
        super(em, Collection.class);
    }

    @Override
    public boolean isRegistered(Collection entity) {
        return super.find(entity.getName()) != null;
    }
    
}
