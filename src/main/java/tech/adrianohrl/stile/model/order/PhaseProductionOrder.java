package tech.adrianohrl.stile.model.order;

import tech.adrianohrl.stile.exceptions.ProductionException;
import tech.adrianohrl.stile.exceptions.ProductionStateMachineException;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.control.model.production.ProductionStateMachineController;
import tech.adrianohrl.stile.model.production.ModelPhase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class PhaseProductionOrder implements Comparable<PhaseProductionOrder>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    private boolean archived = false;
    @ManyToOne(optional = false)
    private ModelPhase phase;
    @ManyToOne(optional = false)
    private ProductionOrder productionOrder;
    @ManyToOne
    private Subordinate subordinate = null;
    private ProductionStates productionState;
    private int producedQuantity = 0;
    private int returnedQuantity = 0;
    private int totalQuantity;
    private boolean pendent = true;

    public PhaseProductionOrder() {
    }

    public PhaseProductionOrder(ModelPhase phase, ProductionOrder productionOrder, int totalQuantity) throws ProductionException {
        this.phase = phase;
        this.productionOrder = productionOrder;
        if (!productionOrder.belongs(phase)) {
            throw new ProductionException("This phase does not belong to the model of the given production order!!!");
        }
        if (totalQuantity < 0) {
            throw new ProductionException("The total quantity must not be negative!!!");
        }
        this.totalQuantity = totalQuantity;
    }
    
    public void process(EntryEvent entryEvent) throws ProductionStateMachineException {
        ProductionStateMachineController controller = new ProductionStateMachineController(this);
        controller.process(entryEvent);
    }
    
    public List<ProductionStates> getPossibleNextStates() {
        try {
            ProductionStateMachineController controller = new ProductionStateMachineController(this);
            return controller.getPossibleNextStates();
        } catch (ProductionStateMachineException e) {}
        return new ArrayList<>();
    }
    
    public boolean isBeingProcessed() {
        return productionState != null && productionState.isStartingState();
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
    public int compareTo(PhaseProductionOrder phaseProductionOrder) {
        int cmp = productionOrder.compareTo(phaseProductionOrder.productionOrder);
        return cmp == 0 ? phase.compareTo(phaseProductionOrder.phase) : cmp;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PhaseProductionOrder && equals((PhaseProductionOrder) obj);
    }

    public boolean equals(PhaseProductionOrder phaseProductionOrder) {
        return phaseProductionOrder != null && productionOrder.equals(phaseProductionOrder.productionOrder) && phase.equals(phaseProductionOrder.phase);
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public ModelPhase getPhase() {
        return phase;
    }

    public void setPhase(ModelPhase phase) {
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
        if (this.subordinate != null) {
            this.subordinate.setAvailable(true);
        }
        if (subordinate != null) {
            subordinate.setAvailable(productionState.isFreerState());
        }
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
