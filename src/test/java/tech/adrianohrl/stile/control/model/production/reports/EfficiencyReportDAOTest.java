package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.dao.DataSource;
import tech.adrianohrl.stile.control.dao.events.AbstractEmployeeRelatedEventDAO;
import tech.adrianohrl.stile.control.dao.events.io.EventsReaderDAOTest;
import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.dao.personnel.PersonnelDAOsTest;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.control.dao.production.ProductionDAOsTest;
import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.DAOException;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
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
