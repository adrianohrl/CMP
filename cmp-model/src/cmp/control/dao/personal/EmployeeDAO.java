/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.personal;

import cmp.control.dao.DAO;
import cmp.model.personal.Employee;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public class EmployeeDAO<E extends Employee> extends DAO<E, String> {

    public EmployeeDAO(EntityManager em) {
        super(em, Employee.class);
    }
    
    protected EmployeeDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }

    @Override
    public boolean isRegistered(E entity) {
        return super.find(entity.getName()) != null;
    }
    
}
