/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.dao.DAO;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
