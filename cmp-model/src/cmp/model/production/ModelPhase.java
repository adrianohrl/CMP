/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class ModelPhase implements Serializable, Comparable<ModelPhase> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    private double expectedDuration;
    @ManyToOne(optional = false)
    private Phase phase;

    public ModelPhase() {
    }

    public ModelPhase(Phase phase, double expectedDuration) {
        this.expectedDuration = expectedDuration;
        this.phase = phase;
    }

    @Override
    public int compareTo(ModelPhase modelPhase) {
        return phase.compareTo(modelPhase.phase);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ModelPhase && equals((ModelPhase) obj) || obj instanceof Phase && equals((Phase) obj));
    }
    
    public boolean equals(ModelPhase modelPhase) {
        return modelPhase != null && phase.equals(modelPhase.phase);
    }
    
    public boolean equals(Phase phase) {
        return phase != null && this.phase.equals(phase);
    }

    @Override
    public String toString() {
        return phase + " (" + expectedDuration + " [min])";
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public double getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(double expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
    
}
