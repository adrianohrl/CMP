/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Phase implements Comparable<Phase>, Serializable {
    
    @Id
    private String name;
    private double expectedDuration;

    public Phase() {
    }

    public Phase(String name, double expectedDuration) {
        this.name = name;
        this.expectedDuration = expectedDuration;
    }

    @Override
    public int compareTo(Phase phase) {
        return name.compareToIgnoreCase(phase.name);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Phase && equals((Phase) obj);
    }
    
    public boolean equals(Phase phase) {
        return phase != null && name.equalsIgnoreCase(phase.name);
    }
    
    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(double expectedDuration) {
        this.expectedDuration = expectedDuration;
    }
    
}
