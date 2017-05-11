/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports.filters;

import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.EntryEvent;
import cmp.model.personal.Subordinate;
import cmp.model.production.Phase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsList extends EmployeeRelatedEventsList<EntryEvent> {

    public EntryEventsList() {
    }

    public EntryEventsList(Collection<? extends EntryEvent> c) {
        super(c);
    }

    public EntryEventsList(EmployeeRelatedEventsList<? extends AbstractEmployeeRelatedEvent> events) {
        for (AbstractEmployeeRelatedEvent event : events) {
            if (event instanceof EntryEvent) {
                super.add((EntryEvent) event);
            }
        }
    }
    
    public List<Phase> getPhases() {
        ArrayList<Phase> phases = new ArrayList<>();
        for (EntryEvent event : this) {
            Phase phase = event.getPhaseProductionOrder().getPhase();
            if (!phases.contains(phase)) {
                phases.add(phase);
            }
        }
        return phases;
    }
    
    public List<Subordinate> getInvolvedSubordinates() {
        List<Subordinate> subordinates = new ArrayList<>();
        for (EntryEvent event : this) {
            Subordinate subordinate = event.getEmployee();
            if (!subordinates.contains(subordinate)) {
                subordinates.add(subordinate);
            }
        }
        Collections.sort(subordinates);
        return subordinates;
    }
    
}
