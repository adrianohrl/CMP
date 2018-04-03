/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class VariantOrder implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    @ManyToOne(optional = false)
    private Variant variant;
    private int xs = 0;
    private int s = 0;
    private int m = 0;
    private int l = 0;
    private int xl = 0;
    private int u = 0;
    private int total = 0;

    public VariantOrder() {
    }

    public VariantOrder(Variant variant) {
        this.variant = variant;
    }
    
    public int getQuantity(Sizes size) {
        switch (size) {
            case XS:
                return xs;
            case S:
                return s;
            case M:
                return m;
            case L:
                return l;
            case XL:
                return xl;
            case U:
                return u;
        }
        return 0;
    }
    
    public void setQuantity(Sizes size, int quantity) {
        switch (size) {
            case XS:
                setXs(quantity);
                break;
            case S:
                setS(quantity);
                break;
            case M:
                setM(quantity);
                break;
            case L:
                setL(quantity);
                break;
            case XL:
                setXl(quantity);
                break;
            case U:
                setU(quantity);
                break;
        }
    }

    @Override
    public String toString() {
        return variant.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof VariantOrder && equals((VariantOrder) obj);
    }
    
    public boolean equals(VariantOrder order) {
        return order != null && variant != null && variant.equals(order.variant);
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

    public int getXs() {
        return xs;
    }

    public void setXs(int xs) {
        if (xs < 0)
        {
            return;
        }
        total += xs - this.xs;
        this.xs = xs;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        if (s < 0)
        {
            return;
        }
        total += s - this.s;
        this.s = s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        if (m < 0)
        {
            return;
        }
        total += m - this.m;
        this.m = m;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        if (l < 0)
        {
            return;
        }
        total += l - this.l;
        this.l = l;
    }

    public int getXl() {
        return xl;
    }

    public void setXl(int xl) {
        if (xl < 0)
        {
            return;
        }
        total += xl - this.xl;
        this.xl = xl;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        if (u < 0)
        {
            return;
        }
        total += u - this.u;
        this.u = u;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
