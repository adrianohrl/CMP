/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Casualty implements Comparable<Casualty>, Serializable {
    
    @Id
    private String name;
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

    public boolean isCollective() {
        return collective;
    }

    public void setCollective(boolean collective) {
        this.collective = collective;
    }
    
}
