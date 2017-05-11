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
import cmp.dao.production.ProductionKeyboardEntries;
import cmp.exceptions.IOException;
import cmp.exceptions.ProductionException;
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
import cmp.production.reports.filters.EntryEventsList;
import cmp.production.reports.filters.FindByEmployee;
import cmp.util.Keyboard;
import cmp.util.KeyboardEntries;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
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
            case REGISTER_CASUALTY:
                EventsTest.createCasualty();
                break;
            case REGISTER_ENTRY_EVENT:
                EventsTest.createEntryEvent();
                break;
            case REGISTER_TIME_CLOCK_EVENT:
                EventsTest.createTimeClockEvent();
                break;
            case REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUBORDINATES:
                EventsTest.createCollectiveEntryEventPerSubordinates();
                break;
            case REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUPERVISOR:
                EventsTest.createCollectiveEntryEventPerSupervisor();
                break;
            case REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUPERVISORS:
                EventsTest.createCollectiveEntryEventPerSupervisors();
                break;
            case REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SECTOR:
                EventsTest.createCollectiveEntryEventPerSector();
                break;
            case REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SECTORS:
                EventsTest.createCollectiveEntryEventPerSectors();
                break;
            case SHOW_ALL_CASUALTIES:
                EventsTest.showAllCasualties();
                break;
            case SHOW_ALL_COLLECTIVE_CASUALTIES:
                EventsTest.showAllCollectiveCasualties();
                break;
            case SHOW_ALL_NON_COLLECTIVE_CASUALTIES:
                EventsTest.showAllNonCollectiveCasualties();
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

    private static void createCasualty() {
        System.out.println("\nRegistering a new casualty ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new casualty below:");
        String name = keyboard.readString("name: ");
        boolean collective = KeyboardEntries.askForYesOrNo("collective");
        try {
            EventsTest.register(new Casualty(name, collective));
            System.out.println("The casualty registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The casualty registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }
    
    private static void createEntryEvent() {
        System.out.println("\nRegistering a new entry event ...");
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
            System.out.println("The entry event registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The entry event registration failed: " + e.getMessage());
            em.clear();
        } catch (ProductionException e) {
            System.out.println("The entry event registration failed: " + e.getMessage());
        }
    }

    private static void createTimeClockEvent() {
        System.out.println("\nRegistering a new time clock event ...");
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

    private static void createCollectiveEntryEventPerSubordinates() {
        ProductionStates state = EventsTest.getProductionState("\nRegistering a new collective entry event for a group of subordinates ...");
        if (state == null) {
            return;
        }
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        EntryEventsList entryEvents = null;
        if (state == ProductionStates.RESTARTED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted();
        } else if (state == ProductionStates.PAUSED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused();
        } 
        EventsTest.createCollectiveEntryEvents(entryEvents, state);
    }

    private static void createCollectiveEntryEventPerSupervisor() {
        ProductionStates state = EventsTest.getProductionState("\nRegistering a new collective entry event for all supervisor subordinates ...");
        if (state == null) {
            return;
        }
        System.out.println("Enter the supervisor:");
        Supervisor supervisor = PersonalKeyboardEntries.selectOneSupervisor();
        if (supervisor == null) {
            return;
        }
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        EntryEventsList entryEvents = null;
        if (state == ProductionStates.RESTARTED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted(supervisor);
        } else if (state == ProductionStates.PAUSED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused(supervisor);
        } 
        EventsTest.createCollectiveEntryEvents(entryEvents, state);
    }

    private static void createCollectiveEntryEventPerSupervisors() {
        ProductionStates state = EventsTest.getProductionState("\nRegistering a new collective entry event for all supervisors subordinates ...");
        if (state == null) {
            return;
        }
        System.out.println("Enter the supervisors:");
        List<Supervisor> supervisors = PersonalKeyboardEntries.selectManySupervisors();
        if (supervisors == null) {
            return;
        }
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        EntryEventsList entryEvents = new EntryEventsList();
        for (Supervisor supervisor : supervisors) {
            if (state == ProductionStates.RESTARTED) {
                entryEvents.addAll(entryEventDAO.findEntryEventsThatCanBeRestarted(supervisor));
            } else if (state == ProductionStates.PAUSED) {
                entryEvents.addAll(entryEventDAO.findEntryEventsThatCanBePaused(supervisor));
            } 
        }
        EventsTest.createCollectiveEntryEvents(entryEvents, state);
    }

    private static void createCollectiveEntryEventPerSector() {
        ProductionStates state = EventsTest.getProductionState("\nRegistering a new collective entry event for all sector subordinates ...");
        if (state == null) {
            return;
        }
        System.out.println("Enter the sector:");
        Sector sector = PersonalKeyboardEntries.selectOneSector();
        if (sector == null) {
            return;
        }
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        EntryEventsList entryEvents = null;
        if (state == ProductionStates.RESTARTED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted(sector);
        } else if (state == ProductionStates.PAUSED) {
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused(sector);
        } 
        EventsTest.createCollectiveEntryEvents(entryEvents, state);
    }

    private static void createCollectiveEntryEventPerSectors() {
        ProductionStates state = EventsTest.getProductionState("\nRegistering a new collective entry event for all sectors subordinates ...");
        if (state == null) {
            return;
        }
        System.out.println("Enter the sectors:");
        List<Sector> sectors = PersonalKeyboardEntries.selectManySectors();
        if (sectors == null) {
            return;
        }
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        EntryEventsList entryEvents = new EntryEventsList();
        for (Sector sector : sectors) {
            if (state == ProductionStates.RESTARTED) {
                entryEvents.addAll(entryEventDAO.findEntryEventsThatCanBeRestarted(sector));
            } else if (state == ProductionStates.PAUSED) {
                entryEvents.addAll(entryEventDAO.findEntryEventsThatCanBePaused(sector));
            } 
        }
        EventsTest.createCollectiveEntryEvents(entryEvents, state);
    }
    
    private static ProductionStates getProductionState(String prompt) {
        System.out.println(prompt);
        System.out.println("Enter the production state:");
        return ProductionKeyboardEntries.selectOneRestartedOrPausedState();
    }

    private static void createCollectiveEntryEvents(EntryEventsList entryEvents, ProductionStates state) {
        if (entryEvents == null) {
            return;
        }
        if (entryEvents.isEmpty()) {
            System.out.println("There is no phase production order that can be taken to this state in the present moment!!!");
        }
        List<Subordinate> subordinates = KeyboardEntries.selectMany(entryEvents.getInvolvedSubordinates(), "subordinate");
        Calendar timestamp = KeyboardEntries.askForDateAndTime();
        Keyboard keyboard = Keyboard.getKeyboard();
        String observation = keyboard.readString("observation: ");        
        FindByEmployee filter;
        List<EntryEvent> filteredEntryEvents = new ArrayList<>();
        for (Subordinate subordinate : subordinates) {
            filter = new FindByEmployee(subordinate);
            entryEvents.execute(filter);
            filteredEntryEvents.addAll(filter.getItems());
        }
        if (state == ProductionStates.RESTARTED) {
            EventsTest.createCollectiveEntryEvents(filteredEntryEvents, timestamp, observation);
        } else if (state == ProductionStates.PAUSED) {
            Casualty casualty = EventsKeyboardEntries.selectOneCollectiveCasualty();
            if (casualty == null) {
                return;
            }
            EventsTest.createCollectiveEntryEvents(filteredEntryEvents, casualty, timestamp, observation);
        }        
    }
    
    private static void createCollectiveEntryEvents(List<EntryEvent> entryEvents, Calendar timestamp, String observation)  {
        Sector sector;
        Supervisor supervisor;
        PhaseProductionOrder phaseProductionOrder;
        Subordinate subordinate;
        EntryEvent entryEvent;
        for (EntryEvent previousEntryEvent : entryEvents) {
            sector = previousEntryEvent.getSector();
            supervisor = previousEntryEvent.getSupervisor();
            phaseProductionOrder = previousEntryEvent.getPhaseProductionOrder();
            subordinate = previousEntryEvent.getSubordinate();
            try {
                entryEvent = new EntryEvent(sector, supervisor, phaseProductionOrder, subordinate, ProductionStates.RESTARTED, timestamp, observation);
                EventsTest.register(entryEvent);
                System.out.println(subordinate + "'s phase production order transaction succeeded!!!");
            } catch (ProductionException e) {
                System.out.println(subordinate + "'s phase production order transaction failed: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(subordinate + "'s phase production order transaction failed: " + e.getMessage());
                em.clear();
            }
        }
    }
    
    private static void createCollectiveEntryEvents(List<EntryEvent> entryEvents, Casualty casualty, Calendar timestamp, String observation)  {
        Sector sector;
        Supervisor supervisor;
        PhaseProductionOrder phaseProductionOrder;
        Subordinate subordinate;
        CasualtyEntryEvent casualtyEntryEvent;
        for (EntryEvent previousEntryEvent : entryEvents) {
            sector = previousEntryEvent.getSector();
            supervisor = previousEntryEvent.getSupervisor();
            phaseProductionOrder = previousEntryEvent.getPhaseProductionOrder();
            subordinate = previousEntryEvent.getSubordinate();
            try {
                casualtyEntryEvent = new CasualtyEntryEvent(casualty, sector, supervisor, phaseProductionOrder, subordinate, ProductionStates.PAUSED, 0, timestamp, observation);
                EventsTest.register(casualtyEntryEvent);
                System.out.println(subordinate + "'s phase production order transaction succeeded!!!");
            } catch (ProductionException e) {
                System.out.println(subordinate + "'s phase production order transaction failed: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(subordinate + "'s phase production order transaction failed: " + e.getMessage());
                em.clear();
            }
        }
    }

    public static void showAllCasualties() {
        System.out.println("Showing all registered casualties ...");
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        for (Casualty casualty : casualtyDAO.findAll()) {
            System.out.println("Casualty: " + casualty + (casualty.isCollective() ? "*" : ""));
        }
        System.out.println("\nOBS.: (*) collective casualty.");
    }

    public static void showAllCollectiveCasualties() {
        System.out.println("Showing all registered collective casualties ...");
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        for (Casualty casualty : casualtyDAO.findCollectives()) {
            System.out.println("Casualty: " + casualty);
        }
    }

    public static void showAllNonCollectiveCasualties() {
        System.out.println("Showing all registered non collective casualties ...");
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        for (Casualty casualty : casualtyDAO.findNonCollectives()) {
            System.out.println("Casualty: " + casualty);
        }
    }

    public static void showAllEntryEvents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void showAllTimeClockEvents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void showAllEvents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void importEntryEvents() {
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
        } catch (IOException e) {
            System.out.println("Entry events importation failed: " + e.getMessage());
        }
    }

    public static void importTimeClockEvents() {
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
        } catch (IOException e) {
            System.out.println("Time clock events importation failed: " + e.getMessage());
        }
    }

    private static void reportPerformancePerSubordinate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void reportPerformancePerSubordinates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void reportPerformancePerSupervisor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void reportPerformancePerSupervisors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void reportPerformancePerSector() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void reportPerformancePerSectors() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void registerCasualties(Collection<Casualty> casualties) {
        for (Casualty casualty : casualties) {
            register(casualty);
        }
    }
    
    public static void register(Casualty casualty) {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        casualtyDAO.create(casualty);
    }
    
    public static void register(EmployeeRelatedEventsList<? extends AbstractEmployeeRelatedEvent> events) {
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
            entryEventDAO.create(event);
        } 
    }
    
}
