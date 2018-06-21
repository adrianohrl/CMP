/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.control.dao.events.TimeClockEventDAO;
import tech.adrianohrl.stile.control.dao.personnel.EmployeeDAO;
import tech.adrianohrl.stile.control.dao.personnel.ManagerDAO;
import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.DAOException;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class AttendanceReportDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        try {
            AttendanceReportDAOTest.test(em);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) throws DAOException, ReportException {
        String employeeCode = "800006";
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        Employee employee = employeeDAO.find(employeeCode);
        Calendar startDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 1);
        Calendar endDate = new GregorianCalendar();
        TimeClockEventDAO timeClockEventDAO = new TimeClockEventDAO(em);
        EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events = timeClockEventDAO.findEmployeeEvents(employee, startDate, endDate);
        String managerLogin = "marcos";
        ManagerDAO managerDAO = new ManagerDAO(em);
        Manager manager = managerDAO.find(managerLogin);
        EmployeeAttendanceReport report = new EmployeeAttendanceReport(employee, events, manager, startDate, endDate);
        ReportNumericSeries<AttendanceSeriesTypes> series = report.getSeries(AttendanceSeriesTypes.TOTAL_QUANTITY);
        System.out.println("\n\tDaily " + series + ":");
        for (Map.Entry<Calendar, Number> entry : series) {
            System.out.println("\t\t" + series.format(entry));
        }
        System.out.println("\t\t-----------------------");
        System.out.println("\t\tPeriod total: " + series.format(series.getTotal()));
    }
    
}
