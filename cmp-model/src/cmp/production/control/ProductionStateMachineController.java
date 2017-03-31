/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.control;

import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionStates;
import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 */
public class ProductionStateMachineController {
    
    private final PhaseProductionOrder order;
    private AbstractProductionState currentState;
    private final StartedState startedState = new StartedState(this);
    private final RestartedState restartedState = new RestartedState(this);
    private final PausedState pausedState = new PausedState(this);
    private final FinishedState finishedState = new FinishedState(this);
    private final ReturnedState returnedState = new ReturnedState(this);
    
    public ProductionStateMachineController(PhaseProductionOrder order) {
        this.order = order;
        startedState.init();
        restartedState.init();
        pausedState.init();
        finishedState.init();
        returnedState.init();
        currentState = startedState;
    }

    public ProductionStateMachineController(PhaseProductionOrder order, ProductionStates state)  throws ProductionStateMachineException {
        this(order);
        currentState = getProductionState(state);
    }
    
    /*public void process(ProductionStates nextState, double producedQuantity) throws ProductionStateMachineException {
        process(getProductionState(nextState));
    }
    
    public void process(AbstractProductionState nextState, double producedQuantity) throws ProductionStateTransitionException {
        if (!currentState.isValidStateTransition(nextState)) {
            throw new ProductionStateTransitionException(currentState, nextState);
        }
        
        if (nextState.equals(finishedState) && order.) {
            
        }
        currentState = nextState;
        order.setPendent(currentState.isPendent());
    }*/
    
    public ArrayList<ProductionStates> getPossibleNextStates() throws ProductionStateMachineException{
        ArrayList<ProductionStates> nextStates = new ArrayList<>();
        for (AbstractProductionState productionState : currentState.getPossibleNextStates()) {
            nextStates.add(getState(productionState));
        }
        return nextStates;
    }

    public StartedState getStartedState() {
        return startedState;
    }

    public RestartedState getRestartedState() {
        return restartedState;
    }

    public PausedState getPausedState() {
        return pausedState;
    }

    public FinishedState getFinishedState() {
        return finishedState;
    }

    public ReturnedState getReturnedState() {
        return returnedState;
    }
    
    private AbstractProductionState getProductionState(ProductionStates state) throws ProductionStateMachineException {
        AbstractProductionState productionState;
        switch (state) {
            case STARTED:
                productionState = startedState;
                break;
            case RESTARTED:
                productionState = restartedState;
                break;
            case PAUSED:
                productionState = pausedState;
                break;
            case FINISHED:
                productionState = finishedState;
                break;
            case RETURNED:
                productionState = returnedState;
                break;
            default:
                throw new ProductionStateMachineException("Inexistent production state: " + state + "!!!");
        }
        return productionState;
    }
    
    private ProductionStates getState(AbstractProductionState productionState) throws ProductionStateMachineException{
        ProductionStates state;
        switch (productionState.toString()) {
            case "STARTED":
                state = ProductionStates.STARTED;
                break;
            case "RESTARTED":
                state = ProductionStates.RESTARTED;
                break;
            case "PAUSED":
                state = ProductionStates.PAUSED;
                break;
            case "FINISHED":
                state = ProductionStates.FINISHED;
                break;
            case "RETURNED":
                state = ProductionStates.RETURNED;
                break;
            default:
                throw new ProductionStateMachineException("Inexistent state name: " + productionState);
        }
        return state;
    }
    
}
