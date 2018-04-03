/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.production.VariantOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class VariantOrderDAO extends DAO<VariantOrder, Long> {

    public VariantOrderDAO(EntityManager em) {
        super(em, VariantOrder.class);
    }

    @Override
    public boolean isRegistered(VariantOrder entity) {
        return super.find(entity.getCode()) != null;
    }
    
}
