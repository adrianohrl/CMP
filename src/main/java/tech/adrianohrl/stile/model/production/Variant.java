/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.production;

import tech.adrianohrl.stile.model.order.VariantOrder;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Variant implements Serializable {
    
    @Id
    private String name;

    public Variant() {
    }

    public Variant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Variant && equals((Variant) obj) || obj instanceof VariantOrder && equals(((VariantOrder) obj).getVariant()));
    }
    
    public boolean equals(Variant variant) {
        return variant != null && name.equals(variant.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
