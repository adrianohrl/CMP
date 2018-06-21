/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.control.dao.events.EventDAOsTest;
import tech.adrianohrl.stile.control.dao.events.io.EventsReaderDAOTest;
import tech.adrianohrl.stile.control.dao.personnel.PersonnelDAOsTest;
import tech.adrianohrl.stile.control.dao.personnel.io.PersonnelReaderDAOTest;
import tech.adrianohrl.stile.control.dao.production.ProductionDAOsTest;
import tech.adrianohrl.stile.control.dao.production.io.ProductionReaderDAOTest;
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
