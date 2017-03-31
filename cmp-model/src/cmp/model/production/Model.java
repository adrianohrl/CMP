/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 */
public class Model implements Comparable<Model> {
    
    private String reference;
    private String name;
    private ArrayList<Phase> phases = new ArrayList<>();

    public Model() {
    }

    public Model(String reference, String name) {
        this.reference = reference;
        this.name = name;
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

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<Phase> phases) {
        this.phases = phases;
    }
    
}
