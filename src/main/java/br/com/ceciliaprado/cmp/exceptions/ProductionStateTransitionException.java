/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.exceptions;

import br.com.ceciliaprado.cmp.control.model.production.AbstractProductionState;

/**
 *
 * @author adrianohrl
 */
public class ProductionStateTransitionException extends ProductionStateMachineException {
    
    public ProductionStateTransitionException(String message) {
        super(message);
    }
    public ProductionStateTransitionException(String message, AbstractProductionState currentState, AbstractProductionState nextState) {
        super(message + currentState + " to " + nextState + "!!!");
    }
    
    public ProductionStateTransitionException(AbstractProductionState currentState, AbstractProductionState nextState) {
        this("Invalid state transition: ", currentState, nextState);
    }
    
}
