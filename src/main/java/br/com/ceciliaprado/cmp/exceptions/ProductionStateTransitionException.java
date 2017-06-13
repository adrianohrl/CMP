/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.exceptions;

import br.com.ceciliaprado.cmp.control.model.production.AbstractProductionState;
import org.apache.log4j.Logger;

/**
 *
 * @author adrianohrl
 */
public class ProductionStateTransitionException extends ProductionStateMachineException {
    
    private static final Logger logger = Logger.getLogger(ProductionStateTransitionException.class);
    
    public ProductionStateTransitionException(String message) {
        super(message, logger);
    }
    public ProductionStateTransitionException(String message, AbstractProductionState currentState, AbstractProductionState nextState) {
        super(message + currentState + " to " + nextState + "!!!", logger);
    }
    
    public ProductionStateTransitionException(AbstractProductionState currentState, AbstractProductionState nextState) {
        this("Invalid state transition: ", currentState, nextState);
    }
    
}
