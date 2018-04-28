/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.order;

import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;
import tech.adrianohrl.stile.model.production.Variant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author adrianohrl
 */
@Entity
public class VariantOrder implements Serializable, Iterable<SizeOrder> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    @ManyToOne(optional = false)
    private Variant variant;
    private int total = 0;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizeOrder> sizeOrders = new ArrayList<>();

    public VariantOrder() {
    }

    public VariantOrder(Variant variant, Chart chart) {
        this.variant = variant;
        for (ChartSize size : chart) {
            sizeOrders.add(new SizeOrder(size));
        }
    }

    @Override
    public Iterator<SizeOrder> iterator() {
        return sizeOrders.iterator();
    }
    
    public int getQuantity(ChartSize size) {
        int index = sizeOrders.indexOf(size);
        return index != -1 ? sizeOrders.get(index).getQuantity() : 0;
    }
    
    public void setQuantity(ChartSize size, int quantity) {
        int index = sizeOrders.indexOf(size);
        if (index == -1) {
            return;
        }
        SizeOrder order = sizeOrders.get(index);
        total -= order.getQuantity();
        order.setQuantity(quantity);
        total += quantity;
    }

    @Override
    public String toString() {
        return variant.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof VariantOrder && equals((VariantOrder) obj) || obj instanceof Variant && equals((Variant) obj));
    }
    
    public boolean equals(VariantOrder order) {
        return order != null && equals(order.variant);
    }
    
    public boolean equals(Variant variant) {
        return this.variant != null && this.variant.equals(variant);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SizeOrder> getSizeOrders() {
        return sizeOrders;
    }

    public void setSizeOrders(List<SizeOrder> sizeOrders) {
        this.sizeOrders = sizeOrders;
    }
    
}
