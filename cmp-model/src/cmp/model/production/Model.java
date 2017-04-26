/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Model implements Comparable<Model>, Serializable {
    
    @Id
    private String reference;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Phase> phases = new ArrayList<>();

    public Model() {
    }

    public Model(String reference, String name) {
        this.reference = reference;
        this.name = name;
    }
    
    public boolean belongs(Phase phase) {
        return phases.contains(phase);
    }

    @Override
    public int compareTo(Model model) {
        return reference.compareToIgnoreCase(model.reference);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Model && equals((Model) obj);
    }
    
    public boolean equals(Model model) {
        return model != null && reference.equalsIgnoreCase(model.reference);
    }

    @Override
    public String toString() {
        return name + " (" + reference + ")";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }
    
}
