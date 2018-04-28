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
public class Family implements Serializable {
    
    @Id
    private String name;
    @ManyToOne(optional = false)
    private Collection collection;

    public Family() {
    }

    public Family(String name, Collection collection) {
        this.name = name;
        this.collection = collection;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Family && equals((Family) obj);
    }
    
    public boolean equals(Family family) {
        return family != null && name.equals(family.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    
}
