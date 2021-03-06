/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.stile.control.production.reports.filters.EntryEventsList;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class CollectiveEntryEventsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        try {
            EntryEventDAO entryEventDAO = new EntryEventDAO(em);
            EntryEventsList entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted();
            System.out.println("\nEntryEventsThatCanBeRestarted");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused();
            System.out.println("\nEntryEventsThatCanBePaused");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            Supervisor supervisor = supervisorDAO.find("Rose");
            entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted(supervisor);
            System.out.println("\nEntryEventsThatCanBeRestarted for " + supervisor + " supervisor");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused(supervisor);
            System.out.println("\nEntryEventsThatCanBePaused for " + supervisor + " supervisor");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
            SectorDAO sectorDAO = new SectorDAO(em);
            Sector sector = sectorDAO.find("Costura");
            entryEvents = entryEventDAO.findEntryEventsThatCanBeRestarted(sector);
            System.out.println("\nEntryEventsThatCanBeRestarted for " + sector + " supervisor");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
            entryEvents = entryEventDAO.findEntryEventsThatCanBePaused(sector);
            System.out.println("\nEntryEventsThatCanBePaused for " + sector + " supervisor");
            for (EntryEvent entryEvent : entryEvents) {
                System.out.println("\t" + entryEvent);
            }
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
