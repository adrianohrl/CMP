package tech.adrianohrl.stile.model.events;

import tech.adrianohrl.util.CalendarFormat;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import tech.adrianohrl.util.CalendarUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@MappedSuperclass
public abstract class AbstractEvent implements Comparable<AbstractEvent>, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    private boolean archived = false;
    private String observation = "";
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Calendar eventDate = CalendarUtil.now();

    public AbstractEvent() {
    }

    public AbstractEvent(Calendar eventDate, String observation) {
        this.eventDate = eventDate;
        this.observation = observation;
    }

    @Override
    public int compareTo(AbstractEvent event) {
        return eventDate.compareTo(event.eventDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof AbstractEvent && equals((AbstractEvent) obj);
    }
    
    public boolean equals(AbstractEvent event) {
        return event != null && eventDate.equals(event.eventDate);
    }
    
    @Override
    public String toString() {
        return "[" + CalendarFormat.format(eventDate) + "]: ";
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }
    
}
