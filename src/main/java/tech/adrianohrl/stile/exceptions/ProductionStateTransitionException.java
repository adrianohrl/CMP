package tech.adrianohrl.stile.exceptions;

import tech.adrianohrl.stile.control.production.AbstractProductionState;
import org.apache.log4j.Logger;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
