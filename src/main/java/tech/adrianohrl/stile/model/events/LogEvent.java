package tech.adrianohrl.stile.model.events;

import tech.adrianohrl.stile.model.personnel.Loggable;
import java.util.Calendar;
import javax.persistence.Entity;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
