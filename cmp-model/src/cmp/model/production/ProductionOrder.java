/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class ProductionOrder implements Comparable<ProductionOrder>, Serializable {
    
    @Id
    private String reference;
    @ManyToOne
    private Model model;

    public ProductionOrder() {
    }

    public ProductionOrder(String reference, Model model) {
        this.reference = reference;
        this.model = model;
    }
    
    public boolean belongs(Phase phase) {
        return model.belongs(phase);
    }

    @Override
    public int compareTo(ProductionOrder productionOrder) {
        return reference.compareToIgnoreCase(productionOrder.reference);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ProductionOrder && equals((ProductionOrder) obj);
    }
    
    public boolean equals(ProductionOrder productionOrder) {
       return productionOrder != null && reference.equalsIgnoreCase(productionOrder.reference);
    }

    @Override
    public String toString() {
        return reference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
}
