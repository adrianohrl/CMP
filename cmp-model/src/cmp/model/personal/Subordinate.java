/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.personal;

import javax.persistence.Entity;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Subordinate extends Employee {
    
    //private boolean available = true;

    public Subordinate() {
    }

    public Subordinate(String code, String name) {
        super(code, name);
    }

    /*public Subordinate(String code, String name, boolean available) {
        super(code, name);
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }*/
    
}
