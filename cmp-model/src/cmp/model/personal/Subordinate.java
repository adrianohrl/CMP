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

    public Subordinate() {
    }

    public Subordinate(String code, String name) {
        super(code, name);
    }
    
}
