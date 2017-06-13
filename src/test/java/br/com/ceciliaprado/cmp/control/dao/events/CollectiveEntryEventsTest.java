/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
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
            System.out.println("Exception catched: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}