/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.personnel;

import br.com.ceciliaprado.cmp.control.dao.DAO;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
    public E find(String code) {
        E employee = super.find(code);
        if (employee == null) {
            try {
                employee = (E) em.createQuery("SELECT e "
                    + "FROM Employee e "
                    + "WHERE e.code = '" + code + "'").getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
        return employee;
    }

    @Override
    public boolean isRegistered(E entity) {
        return super.find(entity.getName()) != null;
    }
    
}
