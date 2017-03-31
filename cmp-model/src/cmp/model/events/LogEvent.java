/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personal.Loggable;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class LogEvent extends AbstractEvent {
    
    private Loggable loggableEmployee;
    private LogStates logState;

    public LogEvent() {
    }

    public LogEvent(Loggable loggableEmployee, LogStates logState, Calendar eventDate, String observation) {
        super(eventDate, observation);
        this.loggableEmployee = loggableEmployee;
        this.logState = logState;
    }

    @Override
    public boolean equals(AbstractEvent event) {
        return event instanceof LogEvent && equals((LogEvent) event);
    }
    
    public boolean equals(LogEvent event) {
        return super.equals(event) && loggableEmployee.equals(event.loggableEmployee);
    }

    @Override
    public String toString() {
        return loggableEmployee + " " + logState + " on " + super.toString();
    }

    public Loggable getLoggableEmployee() {
        return loggableEmployee;
    }

    public void setLoggableEmployee(Loggable loggableEmployee) {
        this.loggableEmployee = loggableEmployee;
    }

    public LogStates getLogState() {
        return logState;
    }

    public void setLogState(LogStates logState) {
        this.logState = logState;
    }
    
}
