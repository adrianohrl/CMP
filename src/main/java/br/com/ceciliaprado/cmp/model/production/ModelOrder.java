/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class ModelOrder implements Serializable {
    
    @Id
    private String name;
    @ManyToOne(optional = false)
    private Model model;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantOrder> quantities = new ArrayList<>();
    @OneToOne
    private VariantOrder totals = new VariantOrder();

    public ModelOrder() {
    }

    public ModelOrder(Model model, String variation) {
        this.model = model;
        this.name = model.getReference() + "-" + variation;
        for (Variant variant : model.getVariants())
        {
            quantities.add(new VariantOrder(variant));
        }
    }
    
    private VariantOrder getVariantOrder(Variant variant) {
        for (VariantOrder order : quantities) {
            if (variant.equals(order.getVariant()))
            {
                return order;
            }
        }
        return null;
    }
    
    public int getQuantity(Variant variant, Sizes size) {
        VariantOrder order = getVariantOrder(variant);
        return order != null ? order.getQuantity(size) : 0;
    }
    
    public int getTotalQuantity(Sizes size) {
        return totals.getQuantity(size);
    }
    
    public int getTotalQuantity() {
        return totals.getTotal();
    }
    
    public void setQuantity(Variant variant, Sizes size, int quantity) {
        VariantOrder order = getVariantOrder(variant);
        if (order == null) {
            return;
        }
        int total = totals.getQuantity(size);
        total -= order.getQuantity(size);
        order.setQuantity(size, quantity);
        total += quantity;
        totals.setQuantity(size, total);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ModelOrder && equals((ModelOrder) obj);
    }
    
    public boolean equals(ModelOrder order) {
        return order != null && name.equals(order.name);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VariantOrder> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<VariantOrder> quantities) {
        this.quantities = quantities;
    }

    public VariantOrder getTotals() {
        return totals;
    }

    public void setTotals(VariantOrder totals) {
        this.totals = totals;
    }
    
}
