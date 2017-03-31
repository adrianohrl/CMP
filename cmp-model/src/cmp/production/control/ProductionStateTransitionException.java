/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.control;

/**
 *
 * @author adrianohrl
 */
public class ProductionStateTransitionException extends ProductionStateMachineException {
    
    public ProductionStateTransitionException(String message) {
        super(message);
    }
    
    public ProductionStateTransitionException(AbstractProductionState currentState, AbstractProductionState nextState) {
        super("Invalid state transition: " + currentState + " to " + nextState + "!!!");
    }
    
}
