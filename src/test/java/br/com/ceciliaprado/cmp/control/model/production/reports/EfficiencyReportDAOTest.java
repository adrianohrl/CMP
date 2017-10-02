package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.AbstractEmployeeRelatedEventDAO;
import br.com.ceciliaprado.cmp.control.dao.events.io.EventsReaderDAOTest;
import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.PersonnelDAOsTest;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionDAOsTest;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.CMPException;
import br.com.ceciliaprado.cmp.exceptions.DAOException;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.awt.color.CMMException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.persistence.EntityManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adrianohrl
 */
public class EfficiencyReportDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        try {
            EfficiencyReportDAOTest.test(em);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) throws DAOException, ReportException {
        try {
            PersonnelDAOsTest.test(em);
            ProductionDAOsTest.test(em);
            EventsReaderDAOTest.test(em);
        } catch (RuntimeException e) {
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        }
        Calendar startDate = new GregorianCalendar(2017, Calendar.OCTOBER, 1);
        Calendar endDate = new GregorianCalendar(2017, Calendar.OCTOBER, 3);
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        Supervisor rose = supervisorDAO.find("Rose");
        ManagerDAO managerDAO = new ManagerDAO(em);
        Manager rafaela = managerDAO.find("Rafaela");
        AbstractEmployeeRelatedEventDAO eventDAO = new AbstractEmployeeRelatedEventDAO(em);
        EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events;
        for (Subordinate subordinate : rose.getSubordinates()) {
            events = eventDAO.findEmployeeEvents(subordinate, startDate, endDate);
            System.out.println("\n\nSubordinate: " + subordinate);
            System.out.println("\n\tEvents:");
            for (AbstractEmployeeRelatedEvent event : events) {
                System.out.println("\t\t" + event);
            }
            System.out.println("\n\t-------------------------------------------------------------");
            SubordinateEfficiencyReport report = new SubordinateEfficiencyReport(subordinate, events, rafaela, startDate, endDate);
            for (ReportNumericSeries<EfficiencySeriesTypes> series : report) {
                System.out.println("\n\n\tDaily " + series + ":");
                for (Map.Entry<Calendar, Number> entry : series) {
                    System.out.println("\t\t" + series.format(entry));
                }
                System.out.println("\t\t-----------------------");
                System.out.println("\t\tPeriod total: " + series.format(series.getTotal()));
            }
        }
    }
    
}
