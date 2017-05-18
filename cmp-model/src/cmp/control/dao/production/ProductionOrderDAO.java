/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production;

import cmp.control.dao.DAO;
import cmp.model.production.ProductionOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ProductionOrderDAO extends DAO<ProductionOrder, String> {

    public ProductionOrderDAO(EntityManager em) {
        super(em, ProductionOrder.class);
    }

    @Override
    public boolean isRegistered(ProductionOrder entity) {
        return super.find(entity.getReference()) != null;
    }
    
}
