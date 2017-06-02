/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import cmp.model.personnel.Sector;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Phase implements Comparable<Phase>, Serializable {
    
    @Id
    private String name;
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

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
    
}
