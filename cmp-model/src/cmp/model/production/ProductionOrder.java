/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

/**
 *
 * @author adrianohrl
 */
public class ProductionOrder implements Comparable<ProductionOrder> {
    
    private String productionOrder;
    private Model model;

    public ProductionOrder() {
    }

    public ProductionOrder(String productionOrder, Model model) {
        this.productionOrder = productionOrder;
        this.model = model;
    }

    @Override
    public int compareTo(ProductionOrder order) {
        return productionOrder.compareToIgnoreCase(order.productionOrder);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ProductionOrder && equals((ProductionOrder) obj);
    }
    
    public boolean equals(ProductionOrder order) {
       return order != null && productionOrder.equalsIgnoreCase(order.productionOrder);
    }

    @Override
    public String toString() {
        return productionOrder;
    }

    public String getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(String productionOrder) {
        this.productionOrder = productionOrder;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
}
