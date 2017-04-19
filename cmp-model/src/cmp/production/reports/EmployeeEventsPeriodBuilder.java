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
    private final ArrayList<AbstractEventsPeriod> eventsPeriods = new ArrayList<>();
    private final ArrayList<TimeClockEventsPeriod> timeClockEventsPeriods = new ArrayList<>();

    public EmployeeEventsPeriodBuilder(Employee employee, EmployeeRelatedEventsList events) throws ReportException {
        this.employee = employee;
        build(events);
    }

    public EmployeeEventsPeriodBuilder(Employee employee, ArrayList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        this(employee, new EmployeeRelatedEventsList(events));
    }
    
    private void build(EmployeeRelatedEventsList events) throws ReportException {
        AbstractEmployeeRelatedEvent previous = null;
        AbstractEmployeeRelatedEvent current;
        for (AbstractEmployeeRelatedEvent event : events) {
            if (event instanceof TimeClockEvent) {
                current = (TimeClockEvent) event;
                if (previous != null) {
                    timeClockEventsPeriods.add((TimeClockEventsPeriod) build(previous, current));
                }
                previous = current;
            }
        }
        Iterator<AbstractEmployeeRelatedEvent> it = events.iterator();
        previous = it.next();
        while (it.hasNext()) {
            current = it.next();
            eventsPeriods.add(build(previous, current));
            previous = current;
        }
    }
    
    private AbstractEventsPeriod build(AbstractEmployeeRelatedEvent first, AbstractEmployeeRelatedEvent last) throws ReportException {
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
        return eventsPeriods.iterator();
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public ArrayList<Phase> getPhases() {
        ArrayList<Phase> phases = new ArrayList<>();
        PhaseProductionOrder phaseProductionOrder;
        Phase phase;
        for (AbstractEventsPeriod eventsPeriod : this) {
            phaseProductionOrder = eventsPeriod.getPhaseProductionOrder();
            if (phaseProductionOrder != null) {
                phase = phaseProductionOrder.getPhase();
                if (!phases.contains(phase)) {
                    phases.add(phase);
                }
            }
        }
        return phases;
    }
    
    public double getTotalExpectedDuration() {
        double total = 0.0;
        for (Phase phase : getPhases()) {
             total += getExpectedDuration(phase);
        }
        return total;
    }
    
    public double getExpectedDuration(Phase phase) {
        return phase.getExpectedDuration() * getProducedQuantity(phase);
    }
    
    public double getTotalEffectiveDuration() {
        double total = 0.0;
        for (Phase phase : getPhases()) {
             total += getEffectiveDuration(phase);
        }
        return total;
    }
    
    public double getEffectiveDuration(Phase phase) {
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
    
    public double getTotalDuration() {
        double duration = 0.0;
        for (AbstractEventsPeriod eventsPeriod : (!timeClockEventsPeriods.isEmpty() ? timeClockEventsPeriods : this)) {
            if (eventsPeriod.isWorkingPeriod()) {
                duration += eventsPeriod.getDuration();
            }
        }
        return duration;
    }
    
    public double getTotalFreeDuration() {
        return getTotalDuration() - getTotalEffectiveDuration();
    }
    
    public double getTotalExpectedFreeDuration() {
        return getTotalDuration() - getTotalExpectedDuration();
    }
    
    public int getTotalProducedQuantity() {
        int total = 0;
        for (Phase phase : getPhases()) {
             total += getProducedQuantity(phase);
        }
        return total;
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
    
    public int getTotalReturnedQuantity() {
        int total = 0;
        for (Phase phase : getPhases()) {
             total += getReturnedQuantity(phase);
        }
        return total;
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
    
    public double getTotalEffectiveEfficiency() {
        double totalEffectiveDuration = getTotalEffectiveDuration();
        return totalEffectiveDuration != 0.0 ? getTotalExpectedDuration() / totalEffectiveDuration : 0.0;
    }
    
    public double getEffectiveEfficiency(Phase phase) {
        double effectiveDuration = getEffectiveDuration(phase);
        return effectiveDuration != 0.0 ? getExpectedDuration(phase) / effectiveDuration : 0.0;
    }
    
    public double getTotalEfficiency() {
        double totalDuration = getTotalDuration();
        return totalDuration != 0.0 ? getTotalEffectiveDuration() / totalDuration : 0.0;
    }
    
}
