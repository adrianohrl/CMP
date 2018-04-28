/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.model.personnel.Manager;
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
