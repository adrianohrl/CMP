/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production;

import br.com.ceciliaprado.cmp.exceptions.ProductionException;
import br.com.ceciliaprado.cmp.exceptions.ProductionStateMachineException;
import br.com.ceciliaprado.cmp.exceptions.ProductionStateTransitionException;
import br.com.ceciliaprado.cmp.model.events.CasualtyEntryEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionStates;
import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 */
public class ProductionStateMachineController {
    
    private final PhaseProductionOrder phaseProductionOrder;
    private final StartedState startedState = new StartedState(this);
    private final RestartedState restartedState = new RestartedState(this);
    private final PausedState pausedState = new PausedState(this);
    private final FinishedState finishedState = new FinishedState(this);
    private final ReturnedState returnedState = new ReturnedState(this);
    private AbstractProductionState currentState = null;
    
    public ProductionStateMachineController(PhaseProductionOrder phaseProductionOrder) throws ProductionStateMachineException {
        if (phaseProductionOrder == null) {
            throw new ProductionStateMachineException("The given phase production order must not be null!!!");
        }
        this.phaseProductionOrder = phaseProductionOrder;
        startedState.init();
        restartedState.init();
        pausedState.init();
        finishedState.init();
        returnedState.init();
        ProductionStates state = phaseProductionOrder.getProductionState();
        if (state != null) {
            currentState = getProductionState(state);
        }
    }
    
    public void process(EntryEvent entryEvent) throws ProductionStateMachineException {
        if (!phaseProductionOrder.equals(entryEvent.getPhaseProductionOrder())) {
            throw new ProductionStateMachineException("The phase production order of the entry event must be equal to this one!!!");
        }
        process(getProductionState(entryEvent.getProductionState()), entryEvent.getSubordinate(), entryEvent.getProducedQuantity());
    }
    
    public void process(CasualtyEntryEvent entryEvent) throws ProductionStateMachineException {
        process(getProductionState(entryEvent.getProductionState()), entryEvent.getSubordinate(), entryEvent.getProducedQuantity(), entryEvent.getReturnedQuantity());
    }
    
    private void process(AbstractProductionState nextState, Subordinate subordinate, int producedQuantity) throws ProductionStateMachineException {
        if (currentState != null) {
            if (!currentState.isValidStateTransition(nextState)) {
                throw new ProductionStateTransitionException(currentState, nextState);
            }
        } else if (!nextState.equals(startedState)) {
            throw new ProductionStateMachineException("The production state machine has not been started yet!!!");
        }
        if (!subordinate.equals(phaseProductionOrder.getSubordinate()))
        {
            if (!nextState.isAllowedToChangeSubordinate())
            {
                throw new ProductionStateTransitionException("It is not allowed to change the subordinated from ", currentState, nextState);
            }
            phaseProductionOrder.setSubordinate(subordinate);
        }
        currentState = nextState;
        phaseProductionOrder.setPendent(currentState.isPendent());
        try {
            phaseProductionOrder.produced(producedQuantity);
        } catch (ProductionException e) {
            throw new ProductionStateMachineException("ProductionException: " + e.getMessage());
        }
        phaseProductionOrder.setProductionState(getState(currentState));
    }
    
    private void process(AbstractProductionState nextState, Subordinate subordinate, int producedQuantity, int returnedQuantity) throws ProductionStateMachineException {
        process(nextState, subordinate, producedQuantity);
        try {
            phaseProductionOrder.returned(returnedQuantity);
        } catch (ProductionException e) {
            throw new ProductionStateMachineException("ProductionException: " + e.getMessage());
        }
    }

    protected StartedState getStartedState() {
        return startedState;
    }

    protected RestartedState getRestartedState() {
        return restartedState;
    }

    protected PausedState getPausedState() {
        return pausedState;
    }

    protected FinishedState getFinishedState() {
        return finishedState;
    }

    protected ReturnedState getReturnedState() {
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
    
    public ArrayList<ProductionStates> getPossibleNextStates() throws ProductionStateMachineException {
        ArrayList<ProductionStates> nextStates = new ArrayList<>();
        if (currentState != null) {
            for (AbstractProductionState productionState : currentState.getPossibleNextStates()) {
                nextStates.add(getState(productionState));
            }
        } else {
            nextStates.add(ProductionStates.STARTED);
        }
        return nextStates;
    }
    
}
