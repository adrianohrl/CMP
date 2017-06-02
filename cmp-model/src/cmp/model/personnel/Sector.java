/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.personnel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Sector implements Comparable<Sector>, Serializable{
    
    @Id
    private String name;
    @ManyToOne(optional = false)
    private Supervisor supervisor;

    public Sector() {
    }

    public Sector(String name, Supervisor supervisor) {
        this.name = name;
        this.supervisor = supervisor;
    }
    
    public boolean isSupervisedBy(Supervisor supervisor) {
        return supervisor != null && supervisor.equals(this.supervisor);
    }

    @Override
    public int compareTo(Sector sector) {
        return name.compareToIgnoreCase(sector.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Sector && equals((Sector) obj);
    }

    public boolean equals(Sector sector) {
        return sector != null && name.equalsIgnoreCase(sector.name);
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

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
    
}
