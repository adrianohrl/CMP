package tech.adrianohrl.stile.model.production;

import tech.adrianohrl.stile.model.personnel.Sector;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Phase implements Comparable<Phase>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    @ManyToOne(optional = false)
    private Sector sector;

    public Phase() {
    }

    public Phase(String name, Sector sector) {
        this.name = name;
        this.sector = sector;
    }

    @Override
    public int compareTo(Phase phase) {
        return name.compareToIgnoreCase(phase.name);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Phase && equals((Phase) obj) || obj instanceof ModelPhase && equals((ModelPhase) obj));
    }
    
    public boolean equals(Phase phase) {
        return phase != null && name.equalsIgnoreCase(phase.name);
    }
    
    public boolean equals(ModelPhase modelPhase) {
        return modelPhase != null && equals(modelPhase.getPhase());
    }
    
    public boolean equals(String phaseName) {
        return name.equals(phaseName);
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
    
}
