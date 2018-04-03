/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.personnel.Machine;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class MachineDAO extends DAO<Machine, String> {
    
    public MachineDAO(EntityManager em) {
        super(em, Machine.class);
    }

    @Override
    public boolean isRegistered(Machine entity) {
        return super.find(entity.getName()) != null;
    }
    
}
