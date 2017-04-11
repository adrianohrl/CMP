/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personal.Employee;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class EmployeeEventsPeriodBuilder implements Iterable<AbstractEventsPeriod> {
    
    private final Employee employee;
    private final ArrayList<AbstractEventsPeriod> eventsPeriod = new ArrayList<>();

    public EmployeeEventsPeriodBuilder(Employee employee, EmployeeRelatedEventsList events) throws ReportException {
        this.employee = employee;
        build(events);
    }

    public EmployeeEventsPeriodBuilder(Employee employee, ArrayList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        this(employee, new EmployeeRelatedEventsList(events));
    }
    
    private void build(EmployeeRelatedEventsList events) throws ReportException {
        Iterator<AbstractEmployeeRelatedEvent> it = events.iterator();
        AbstractEmployeeRelatedEvent previous = it.next();
        AbstractEmployeeRelatedEvent current;
        while (it.hasNext()) {
            current = it.next();
            eventsPeriod.add(create(previous, current));
            previous = current;
        }
    }
    
    private AbstractEventsPeriod create(AbstractEmployeeRelatedEvent first, AbstractEmployeeRelatedEvent last) throws ReportException {
        AbstractEventsPeriod eventPeriod;
        if (first instanceof EntryEvent && last instanceof EntryEvent) {
            eventPeriod = new EntryEventsPeriod((EntryEvent) first, (EntryEvent) last);
        } else if (first instanceof EntryEvent && last instanceof TimeClockEvent) {
            eventPeriod = new EntryTimeClockEventsPeriod((EntryEvent) first, (TimeClockEvent) last);
        } else if (first instanceof TimeClockEvent && last instanceof EntryEvent) {
            eventPeriod = new TimeClockEntryEventsPeriod((TimeClockEvent) first, (EntryEvent) last);
        } else if (first instanceof TimeClockEvent && last instanceof TimeClockEvent) {
            eventPeriod = new TimeClockEventsPeriod((TimeClockEvent) first, (TimeClockEvent) last);
        } else {
            throw new ReportException("Invalid event period: " + first + " to " + last);
        }
        return eventPeriod;
    }

    @Override
    public Iterator<AbstractEventsPeriod> iterator() {
        return eventsPeriod.iterator();
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public ArrayList<Phase> getPhases() {
        ArrayList<Phase> phases = new ArrayList<>();
        PhaseProductionOrder phaseProductionOrder;
        Phase phase;
        for (AbstractEventsPeriod eventPeriod : this) {
            phaseProductionOrder = eventPeriod.getPhaseProductionOrder();
            if (phaseProductionOrder != null) {
                phase = phaseProductionOrder.getPhase();
                if (!phases.contains(phase)) {
                    phases.add(phase);
                }
            }
        }
        return phases;
    }
    
    public double getDuration(Phase phase) {
        double duration = 0.0;
        PhaseProductionOrder phaseProductionOrder;
        for (AbstractEventsPeriod eventPeriod : this) {
            phaseProductionOrder = eventPeriod.getPhaseProductionOrder();
            if (phaseProductionOrder != null && phase.equals(phaseProductionOrder.getPhase())) {
                duration += eventPeriod.getDuration();
            }
        }
        return duration;
    }
    
    public int getProducedQuantity(Phase phase) {
        int producedQuantity = 0;
        PhaseProductionOrder phaseProductionOrder;
        for (AbstractEventsPeriod eventPeriod : this) {
            phaseProductionOrder = eventPeriod.getPhaseProductionOrder();
            if (phaseProductionOrder != null && phase.equals(phaseProductionOrder.getPhase())) {
                producedQuantity += eventPeriod.getProducedQuantity();
            }
        }
        return producedQuantity;
    }
    
    public int getReturnedQuantity(Phase phase) {
        int returnedQuantity = 0;
        PhaseProductionOrder phaseProductionOrder;
        for (AbstractEventsPeriod eventPeriod : this) {
            phaseProductionOrder = eventPeriod.getPhaseProductionOrder();
            if (phaseProductionOrder != null && phase.equals(phaseProductionOrder.getPhase())) {
                returnedQuantity += eventPeriod.getReturnedQuantity();
            }
        }
        return returnedQuantity;
    }
    
    public double getExpectedWorkingDuration(Phase phase) {
        return phase.getExpectedDuration() * getProducedQuantity(phase);
    }
    
    public double getEffectiveWorkingDuration(Phase phase) {
        return getDuration(phase) * getProducedQuantity(phase);
    }
    
}
