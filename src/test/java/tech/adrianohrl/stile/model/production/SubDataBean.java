/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.production;

/**
 *
 * @author adrianohrl
 */
public class SubDataBean {
    
    private String variant;
    private int quantity;

    public SubDataBean() {
    }

    public SubDataBean(String variant, int quantity) {
        this.variant = variant;
        this.quantity = quantity;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
