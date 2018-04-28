/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.order;

import tech.adrianohrl.stile.model.production.ChartSize;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Variant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author adrianohrl
 */
@Entity
public class ProductionOrder implements Comparable<ProductionOrder>, Serializable, Iterable<VariantOrder> {
    
    @Id
    private String reference;
    @ManyToOne(optional = false)
    private Model model;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantOrder> variantOrders = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizeOrder> totals = new ArrayList<>();

    public ProductionOrder() {
    }

    public ProductionOrder(String reference, Model model) {
        this.reference = reference;
        this.model = model;
        for (Variant variant : model.getVariants()) {
            variantOrders.add(new VariantOrder(variant, model.getChart()));
        }
        for (ChartSize size : model.getChart()) {
            totals.add(new SizeOrder(size));
        }
    }

    @Override
    public Iterator<VariantOrder> iterator() {
        return variantOrders.iterator();
    }
    
    public int getQuantity(Variant variant, ChartSize size) {
        int index = variantOrders.indexOf(variant);
        return index != -1 ? variantOrders.get(index).getQuantity(size) : 0;
    }
    
    public void setQuantity(Variant variant, ChartSize size, int quantity) {
        int index = totals.indexOf(size);
        if (index == -1) {
            return;
        }
        SizeOrder total = totals.get(index);
        total.setQuantity(total.getQuantity() + quantity - getQuantity(variant, size));
        index = variantOrders.indexOf(variant);
        if (index == -1) {
            return;
        }
        VariantOrder order = variantOrders.get(index);
        order.setQuantity(size, quantity);
    }
    
    public int getTotal() {
        int total = 0;
        for (SizeOrder order : totals) {
            total += order.getQuantity();
        }
        return total;
    }
    
    public int getTotal(ChartSize size) {
        int index = totals.indexOf(size);
        return index != -1 ? totals.get(index).getQuantity() : 0;
    }
    
    public int getTotal(Variant variant) {
        int index = variantOrders.indexOf(variant);
        return index != -1 ? variantOrders.get(index).getTotal() : 0;
    }
    
    public boolean belongs(ModelPhase phase) {
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
    
    public boolean equals(String productionOrderReference) {
        return reference.equalsIgnoreCase(productionOrderReference);
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

    public List<VariantOrder> getVariantOrders() {
        return variantOrders;
    }

    public void setVariantOrders(List<VariantOrder> variantOrders) {
        this.variantOrders = variantOrders;
    }

    public List<SizeOrder> getTotals() {
        return totals;
    }

    public void setTotals(List<SizeOrder> totals) {
        this.totals = totals;
    }
    
}
