/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personal.Loggable;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author adrianohrl
 */
@Entity
public class LogEvent extends AbstractEmployeeRelatedEvent<Loggable> {
    
    private LogStates logState;

    public LogEvent() {
    }

    public LogEvent(Loggable loggableEmployee, LogStates logState, Calendar eventDate, String observation) {
        super(loggableEmployee, eventDate, observation);
        this.logState = logState;
    }

    @Override
    public boolean equals(AbstractEvent event) {
        return event instanceof LogEvent && super.equals((LogEvent) event);
    }

    @Override
    public String toString() {
        return super.toString() + getEmployee() + " is " + logState;
    }

    public LogStates getLogState() {
        return logState;
    }

    public void setLogState(LogStates logState) {
        this.logState = logState;
    }
    
}
