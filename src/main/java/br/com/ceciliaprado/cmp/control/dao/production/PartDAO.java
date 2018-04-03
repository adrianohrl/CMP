/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.production.Part;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class PartDAO extends DAO<Part, String> {

    public PartDAO(EntityManager em) {
        super(em, Part.class);
    }

    @Override
    public boolean isRegistered(Part entity) {
        return super.find(entity.getName()) != null;
    }
    
}
