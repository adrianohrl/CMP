/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.ChartSize;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ChartSizeDAO extends DAO<ChartSize, String> {

    public ChartSizeDAO(EntityManager em) {
        super(em, ChartSize.class);
    }

    @Override
    public boolean isRegistered(ChartSize entity) {
        return super.find(entity.getName()) != null;
    }
    
}
