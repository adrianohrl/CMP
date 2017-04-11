/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports.filters;

import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.EntryEvent;
import cmp.model.production.Phase;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsList extends EventsList<EntryEvent> {

    public EntryEventsList() {
    }

    public EntryEventsList(ArrayList<AbstractEmployeeRelatedEvent> events) {
        for (AbstractEmployeeRelatedEvent event : events) {
            if (event instanceof EntryEvent) {
                super.add((EntryEvent) event);
            }
        }
    }

    public EntryEventsList(Collection<? extends EntryEvent> c) {
        super(c);
    }
    
    public ArrayList<Phase> getPhases() {
        ArrayList<Phase> phases = new ArrayList<>();
        for (EntryEvent event : this) {
            Phase phase = event.getPhaseProductionOrder().getPhase();
            if (!phases.contains(phase)) {
                phases.add(phase);
            }
        }
        return phases;
    }
    
}
