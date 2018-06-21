package tech.adrianohrl.stile.model.events;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Casualty implements Archivable, Comparable<Casualty>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    @Column(updatable = false)
    private boolean collective = false;

    public Casualty() {
    }

    public Casualty(String name) {
        this.name = name;
    }

    public Casualty(String name, boolean collective) {
        this.name = name;
        this.collective = collective;
    }

    @Override
    public int compareTo(Casualty casualty) {
        return name.compareToIgnoreCase(casualty.name);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Casualty && equals((Casualty) obj);
    }
    
    public boolean equals(Casualty casualty) {
        return casualty != null && name.equalsIgnoreCase(casualty.name);
    }
    
    public boolean equals(String casualtyName) {
        return name.equalsIgnoreCase(casualtyName);
    }
    
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isCollective() {
        return collective;
    }

    public void setCollective(boolean collective) {
        this.collective = collective;
    }
    
}
