/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.dao.DataSource;
import cmp.dao.events.io.EntryEventsReaderDAO;
import cmp.dao.events.io.TimeClockEventsReaderDAO;
import cmp.dao.personal.PersonalKeyboardEntries;
import cmp.dao.production.PhaseProductionOrderDAO;
import cmp.dao.production.ProductionKeyboardEntries;
import cmp.exceptions.IOException;
import cmp.exceptions.ProductionException;
import cmp.exceptions.ProductionStateMachineException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.Casualty;
import cmp.model.events.CasualtyEntryEvent;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personal.Employee;
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionStates;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.util.Calendars;
import cmp.util.Keyboard;
import cmp.util.KeyboardEntries;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        EventsMenuOptions option = EventsMenuOptions.getOption();
        while (!option.quit()) {
            try {
                EventsTest.process(option);
            } catch (RuntimeException e) {
                System.out.println("Exception catched: " + e.getMessage());
            }
            option = EventsMenuOptions.getOption();
        }
        System.out.println("Quitting!!!");
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void process(EventsMenuOptions option) {
        if (option.quit()) {
            return;
        }
        switch (option) {
            case REGISTER_ENTRY_EVENT:
                EventsTest.createEntryEvent();
                break;
            case REGISTER_TIME_CLOCK_EVENT:
                EventsTest.createTimeClockEvent();
                break;
            case REGISTER_CASUALTY_ENTRY_EVENT_PER_SUBORDINATES:
                EventsTest.createCasualtyEntryEventPerSubordinates();
                break;
            case REGISTER_CASUALTY_ENTRY_EVENT_PER_SUPERVISOR:
                EventsTest.createCasualtyEntryEventPerSupervisor();
                break;
            case REGISTER_CASUALTY_ENTRY_EVENT_PER_SUPERVISORS:
                EventsTest.createCasualtyEntryEventPerSupervisors();
                break;
            case REGISTER_CASUALTY_ENTRY_EVENT_PER_SECTOR:
                EventsTest.createCasualtyEntryEventPerSector();
                break;
            case REGISTER_CASUALTY_ENTRY_EVENT_PER_SECTORS:
                EventsTest.createCasualtyEntryEventPerSectors();
                break;
            case SHOW_ALL_ENTRY_EVENTS:
                EventsTest.showAllEntryEvents();
                break;
            case SHOW_ALL_TIME_CLOCK_EVENTS:
                EventsTest.showAllTimeClockEvents();
                break;
            case SHOW_ALL_EVENTS:
                EventsTest.showAllEvents();
                break;
            case IMPORT_ENTRY_EVENTS:
                EventsTest.importEntryEvents();
                break;
            case IMPORT_TIME_CLOCK_EVENTS:
                EventsTest.importTimeClockEvents();
                break;
            case REPORT_PER_SUBORDINATE:
                EventsTest.reportPerformancePerSubordinate();
                break;
            case REPORT_PER_SUBORDINATES:
                EventsTest.reportPerformancePerSubordinates();
                break;
            case REPORT_PER_SUPERVISOR:
                EventsTest.reportPerformancePerSupervisor();
                break;
            case REPORT_PER_SUPERVISORS:
                EventsTest.reportPerformancePerSupervisors();
                break;
            case REPORT_PER_SECTOR:
                EventsTest.reportPerformancePerSector();
                break;
            case REPORT_PER_SECTORS:
                EventsTest.reportPerformancePerSectors();
                break;
            default:
                System.out.println("Invalid option!!!");
        }
    }
    
    private static void createEntryEvent() {
        System.out.println("\nRegistering entry event ...");
        System.out.println("Enter the supervisor:");
        Supervisor supervisor = PersonalKeyboardEntries.selectOneSupervisor();
        EventsTest.createEntryEvent(supervisor);
    }

    private static void createEntryEvent(Supervisor supervisor) {
        Subordinate subordinate = PersonalKeyboardEntries.selectOneSubordinateOfSupervisor(supervisor.getName());
        if (subordinate == null) {
            return;
        }
        EventsTest.createEntryEvent(supervisor, subordinate);
    }

    private static void createEntryEvent(Supervisor supervisor, Subordinate subordinate) {
        Sector sector = PersonalKeyboardEntries.selectOneSector(supervisor);
        if (sector == null) {
            return;
        }
        PhaseProductionOrder phaseProductionOrder = ProductionKeyboardEntries.selectOnePhaseProductionOrder();
        if (phaseProductionOrder == null) {
            return;
        }
        Calendar timestamp = KeyboardEntries.askForDateAndTime();
        ProductionStates state = KeyboardEntries.selectOne(phaseProductionOrder.getPossibleNextStates(), "production state: ");
        Casualty casualty = null;
        int producedQuantity = 0;
        if (state.hasCasualty()) {
            casualty = EventsKeyboardEntries.selectOneCasualty();
            if (casualty == null) {
                return;
            }
            producedQuantity = KeyboardEntries.askForPositiveInteger("produced quantity: ");
        }
        Keyboard keyboard = Keyboard.getKeyboard();
        String observation = keyboard.readString("observation: ");
        EntryEvent entryEvent = null;
        try {
            switch (state) {
                case STARTED:
                case RESTARTED:
                case FINISHED:
                    entryEvent = new EntryEvent(sector, supervisor, phaseProductionOrder, subordinate, state, timestamp, observation);
                    break;
                case PAUSED:
                case RETURNED:
                    entryEvent = new CasualtyEntryEvent(casualty, sector, supervisor, phaseProductionOrder, subordinate, state, producedQuantity, timestamp, observation);
                    break;
                default:
                    System.out.println("Invalid production state!!!");
            }
            EventsTest.register(entryEvent);
            phaseProductionOrder.process(entryEvent);
            PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
            phaseProductionOrderDAO.update(phaseProductionOrder);
        } catch (ProductionException pe) {
            System.out.println("Production exception catched: " + pe.getMessage());
        } catch (ProductionStateMachineException ex) {
            System.out.println("Production state machine exception catched: " + ex.getMessage());
        } catch (RuntimeException re) {
            System.out.println("Runtime exception catched: " + re.getMessage());
            em.clear();
        }
    }

    private static void createTimeClockEvent() {
        System.out.println("\nRegistering time clock event ...");
        Employee employee = PersonalKeyboardEntries.selectOneEmployee();
        if (employee == null) {
            return;
        }
        EventsTest.createTimeClockEvent(employee);
    }
    
    private static void createTimeClockEvent(Employee employee) {
        System.out.println("\nRegistering " + employee + "'s time event ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the " + employee + "'s new time clock event below:");
        Calendar timestamp = KeyboardEntries.askForDateAndTime();
        boolean arrival = KeyboardEntries.askForYesOrNo("arrival");
        String observation = keyboard.readString("observation: ");
        try {
            TimeClockEvent timeClockEvent = new TimeClockEvent(employee, arrival, timestamp, observation);
            EventsTest.register(timeClockEvent);
            System.out.println("The time clock event registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The time clock event registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
        
    }

    private static void createCasualtyEntryEventPerSubordinates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createCasualtyEntryEventPerSupervisor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createCasualtyEntryEventPerSupervisors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createCasualtyEntryEventPerSector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createCasualtyEntryEventPerSectors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void showAllEntryEvents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void showAllTimeClockEvents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void showAllEvents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void importEntryEvents() {
        System.out.println("\nImporting entry events ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        try {
            EntryEventsReaderDAO reader = new EntryEventsReaderDAO(em, fileName);
            reader.readFile();
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(reader.getEmployeeRelatedEventsList());
            EventsTest.register(events);
            System.out.println("Entry events importation succeeded!!!");
        } catch (IOException ioe) {
            System.out.println("Entry events importation failed: " + ioe.getMessage());
        }
    }

    private static void importTimeClockEvents() {
        System.out.println("\nImporting time clock events ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        String fileName = keyboard.readString("Enter the file name: ");
        try {
            TimeClockEventsReaderDAO reader = new TimeClockEventsReaderDAO(em, fileName);
            reader.readFile();
            EmployeeRelatedEventsList events = new EmployeeRelatedEventsList();
            events.addAll(reader.getEmployeeRelatedEventsList());
            EventsTest.register(events);
            System.out.println("Time clock events importation succeeded!!!");
        } catch (IOException ioe) {
            System.out.println("Time clock events importation failed: " + ioe.getMessage());
        }
    }

    private static void reportPerformancePerSubordinate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void reportPerformancePerSubordinates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void reportPerformancePerSupervisor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void reportPerformancePerSupervisors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void reportPerformancePerSector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void reportPerformancePerSectors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void register(EmployeeRelatedEventsList events) {
        for (AbstractEmployeeRelatedEvent event : events) {
            EventsTest.register(event);
        }
    }
    
    public static void register(AbstractEmployeeRelatedEvent event) {
        if (event instanceof TimeClockEvent) {
            TimeClockEventDAO timeClockEventDAO = new TimeClockEventDAO(em);
            timeClockEventDAO.create((TimeClockEvent) event);
        } else if (event instanceof CasualtyEntryEvent) {
            CasualtyEntryEventDAO casualtyEntryEventDAO = new CasualtyEntryEventDAO(em);
            casualtyEntryEventDAO.create((CasualtyEntryEvent) event);
        } else if (event instanceof EntryEvent) {
            EntryEventDAO entryEventDAO = new EntryEventDAO(em);
            entryEventDAO.create((EntryEvent) event);
        } 
    }
    
}
