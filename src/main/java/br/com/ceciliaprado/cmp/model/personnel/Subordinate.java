/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.personnel;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Subordinate extends Employee {
    
    private boolean available = true;
    @ManyToMany
    private List<Sector> sectors = new ArrayList<>();

    public Subordinate() {
    }

    public Subordinate(String code, String name) {
        super(code, name);
    }

    public Subordinate(String code, String name, boolean available) {
        super(code, name);
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }
    
}
