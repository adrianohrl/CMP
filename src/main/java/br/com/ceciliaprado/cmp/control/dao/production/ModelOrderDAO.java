/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.production.ModelOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ModelOrderDAO extends DAO<ModelOrder, String> {

    public ModelOrderDAO(EntityManager em) {
        super(em, ModelOrder.class);
    }

    @Override
    public boolean isRegistered(ModelOrder entity) {
        return super.find(entity.getName()) != null;
    }
    
}
