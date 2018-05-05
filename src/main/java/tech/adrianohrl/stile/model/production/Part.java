/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Part implements Comparable<Part>, Serializable {
    
    @Id
    private String name;
    private String program;
    private String observation = "";
    @ManyToOne(optional = false)
    private Fabric fabric;

    public Part() {
    }

    public Part(String name, String program, String observation, Fabric fabric) {
        this.name = name;
        this.program = program;
        this.observation = observation;
        this.fabric = fabric;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Part part) {
        return name.compareTo(part.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Part && equals((Part) obj);
    }
    
    public boolean equals(Part part) {
        return part != null && name.equals(part.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Fabric getFabric() {
        return fabric;
    }

    public void setFabric(Fabric fabric) {
        this.fabric = fabric;
    }
    
}
