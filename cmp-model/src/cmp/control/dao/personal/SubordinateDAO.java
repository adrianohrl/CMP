/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personal;

import cmp.model.personal.Subordinate;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SubordinateDAO extends EmployeeDAO<Subordinate> {

    public SubordinateDAO(EntityManager em) {
        super(em, Subordinate.class);
    }
    
}
