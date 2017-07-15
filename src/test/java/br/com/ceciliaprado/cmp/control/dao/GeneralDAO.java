/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao;

import br.com.ceciliaprado.cmp.control.dao.events.EventDAOsTest;
import br.com.ceciliaprado.cmp.control.dao.events.io.EventsReaderDAOTest;
import br.com.ceciliaprado.cmp.control.dao.personnel.PersonnelDAOsTest;
import br.com.ceciliaprado.cmp.control.dao.personnel.io.PersonnelReaderDAOTest;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionDAOsTest;
import br.com.ceciliaprado.cmp.control.dao.production.io.ProductionReaderDAOTest;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class GeneralDAO {
    
    public static void main(String[] args) {
        EntityManager em = DataSource.createEntityManager();
        PersonnelDAOsTest.test(em);
        PersonnelReaderDAOTest.test(em);
        ProductionDAOsTest.test(em);
        ProductionReaderDAOTest.test(em);
        EventDAOsTest.test(em);
        EventsReaderDAOTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
