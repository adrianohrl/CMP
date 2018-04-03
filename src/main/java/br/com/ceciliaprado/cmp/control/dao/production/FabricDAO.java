/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.production.Fabric;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class FabricDAO extends DAO<Fabric, String> {

    public FabricDAO(EntityManager em) {
        super(em, Fabric.class);
    }

    @Override
    public boolean isRegistered(Fabric entity) {
        return super.find(entity.getName()) != null;
    }
    
}
