/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.util.Command;
import br.com.ceciliaprado.cmp.util.Execute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class EmployeeEventsPeriodBuilder implements Iterable<AbstractEventsPeriod>, Execute<AbstractEmployeeRelatedEvent> {
    
    private final Employee employee;
    private final EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events = new EmployeeRelatedEventsList<>();
    private final List<AbstractEventsPeriod> eventsPeriods = new ArrayList<>();
    private final List<TimeClockEventsPeriod> timeClockEventsPeriods = new ArrayList<>();

    public EmployeeEventsPeriodBuilder(Employee employee, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        this.employee = employee;
        build(events);        
    }
    
    private void build(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        AbstractEmployeeRelatedEvent previous = null;
        TimeClockEvent previousTimeClockEvent = null;
        for (AbstractEmployeeRelatedEvent current : events) {
            if (!employee.equals(current.getEmployee())) {
                throw new ReportException("This is not a " + current.getEmployee() + " builder!!!");
            }
            this.events.add(current);
            if (previous != null) {
                eventsPeriods.add(build(previous, current));
            }
            if (current instanceof TimeClockEvent) {
                if (previousTimeClockEvent != null) {
                    timeClockEventsPeriods.add((TimeClockEventsPeriod) build(previousTimeClockEvent, (TimeClockEvent) current));
                }
                previousTimeClockEvent = (TimeClockEvent) current;
            }
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

    @Override
    public void execute(Command<AbstractEmployeeRelatedEvent> command) {
        events.execute(command);
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public ArrayList<ModelPhase> getPhases() {
        ArrayList<ModelPhase> phases = new ArrayList<>();
        PhaseProductionOrder phaseProductionOrder;
        ModelPhase phase;
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
        for (ModelPhase phase : getPhases()) {
             total += getExpectedDuration(phase);
        }
        return total;
    }
    
    public double getExpectedDuration(ModelPhase phase) {
        return phase.getExpectedDuration() * getProducedQuantity(phase);
    }
    
    public double getTotalEffectiveDuration() {
        double total = 0.0;
        for (ModelPhase phase : getPhases()) {
             total += getEffectiveDuration(phase);
        }
        return total;
    }
    
    public double getEffectiveDuration(ModelPhase phase) {
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
        for (ModelPhase phase : getPhases()) {
             total += getProducedQuantity(phase);
        }
        return total;
    }
    
    public int getProducedQuantity(ModelPhase phase) {
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
        for (ModelPhase phase : getPhases()) {
             total += getReturnedQuantity(phase);
        }
        return total;
    }
    
    public int getReturnedQuantity(ModelPhase phase) {
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
    
    public double getEffectiveEfficiency(ModelPhase phase) {
        double effectiveDuration = getEffectiveDuration(phase);
        return effectiveDuration != 0.0 ? getExpectedDuration(phase) / effectiveDuration : 0.0;
    }
    
    public double getTotalEfficiency() {
        double totalDuration = getTotalDuration();
        return totalDuration != 0.0 ? getTotalEffectiveDuration() / totalDuration : 0.0;
    }
    
}
