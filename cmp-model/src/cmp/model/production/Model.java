/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelPhase> phases = new ArrayList<>();

    public Model() {
    }

    public Model(String reference, String name) {
        this.reference = reference;
        this.name = name;
    }
    
    public boolean belongs(ModelPhase phase) {
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
    
    public ModelPhase getPhase(Phase phase) {
        int index = phases.indexOf(phase);
        return index != -1 ? phases.get(index) : null;
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

    public List<ModelPhase> getPhases() {
        return phases;
    }

    public void setPhases(List<ModelPhase> phases) {
        this.phases = phases;
    }
    
}
