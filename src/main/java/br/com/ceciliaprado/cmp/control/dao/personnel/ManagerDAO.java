/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel;

import br.com.ceciliaprado.cmp.model.personnel.Manager;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ManagerDAO extends LoggableDAO<Manager> {

    public ManagerDAO(EntityManager em) {
        super(em, Manager.class);
    }
    
}
