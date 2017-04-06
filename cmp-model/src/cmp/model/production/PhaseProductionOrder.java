/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.production;

import cmp.exceptions.ProductionException;
import cmp.model.personal.Subordinate;

/**
 *
 * @author adrianohrl
 */
public class PhaseProductionOrder implements Comparable<PhaseProductionOrder> {

    private long code;
    private Phase phase;
    private ProductionOrder productionOrder;
    private Subordinate subordinate;
    private ProductionStates productionState;
    private int producedQuantity = 0;
    private int returnedQuantity = 0;
    private int totalQuantity;
    private boolean pendent = true;

    public PhaseProductionOrder() {
    }

    public PhaseProductionOrder(Phase phase, ProductionOrder productionOrder, Subordinate subordinate, int totalQuantity) throws ProductionException {
        this.phase = phase;
        this.productionOrder = productionOrder;
        this.subordinate = subordinate;
        if (totalQuantity <= 0) {
            throw new ProductionException("The total quantity must be positive!!!");
        }
        this.totalQuantity = totalQuantity;
    }

    /**
     * This constructor should not be used
     * @param phase
     * @param productionOrder
     * @param subordinate
     * @param producedQuantity
     * @param returnedQuantity
     * @param totalQuantity
     * @param productionState
     * @param pendent
     * @throws ProductionException 
     */
    public PhaseProductionOrder(Phase phase, ProductionOrder productionOrder, Subordinate subordinate, int producedQuantity, int returnedQuantity, int totalQuantity, ProductionStates productionState, boolean pendent) throws ProductionException{
        this(phase, productionOrder, subordinate, totalQuantity);
        if (returnedQuantity <= 0) {
            throw new ProductionException("The returned quantity must be positive!!!");
        }
        this.returnedQuantity = returnedQuantity;
        if (producedQuantity <= 0) {
            throw new ProductionException("The produced quantity must be positive!!!");
        }
        this.producedQuantity = producedQuantity;
        this.productionState = productionState;
        this.pendent = pendent;
    }
    
    public void produced(int quantity) throws ProductionException {
        if (quantity < 0)
        {
            throw new ProductionException("The produced quantity must not be negative!!!");
        }
        else if (quantity + producedQuantity + returnedQuantity > totalQuantity) {
            throw new ProductionException("The input produced quantity exceeds the total quantity!!!");
        }
        producedQuantity += quantity;
    }
    
    public boolean returned(int quantity) throws ProductionException {
        if (quantity < 0)
        {
            throw new ProductionException("The returned quantity must not be negative!!!");
        }
        else if (quantity + producedQuantity + returnedQuantity > totalQuantity) {
            throw new ProductionException("The input returned quantity exceeds the total quantity!!!");
        }
        returnedQuantity += quantity;
        return true;
    }
    
    public boolean isDone() {
        return producedQuantity + returnedQuantity == totalQuantity;
    }
    
    public int getRemaingQuantity() {
        return totalQuantity - producedQuantity - returnedQuantity;
    }

    @Override
    public int compareTo(PhaseProductionOrder order) {
        int cmp = productionOrder.compareTo(order.productionOrder);
        return cmp == 0 ? phase.compareTo(order.phase) : cmp;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PhaseProductionOrder && equals((PhaseProductionOrder) obj);
    }

    public boolean equals(PhaseProductionOrder order) {
        return order != null && productionOrder.equals(order.productionOrder) && phase.equals(order.phase);
    }

    @Override
    public String toString() {
        return productionState + " " + phase + " phase of " + 
                productionOrder + " order: produced " + 
                producedQuantity + " [un] of " + totalQuantity + " [un]";
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(ProductionOrder productionOrder) {
        this.productionOrder = productionOrder;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Subordinate subordinate) {
        this.subordinate = subordinate;
    }

    public ProductionStates getProductionState() {
        return productionState;
    }

    public void setProductionState(ProductionStates productionState) {
        this.productionState = productionState;
    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public boolean isPendent() {
        return pendent;
    }

    public void setPendent(boolean pendent) {
        this.pendent = pendent;
    }

}
