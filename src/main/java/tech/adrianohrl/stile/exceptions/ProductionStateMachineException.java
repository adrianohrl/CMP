package tech.adrianohrl.stile.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionStateMachineException extends ProductionException {
    
    private static final Logger logger = Logger.getLogger(ProductionStateMachineException.class);

    public ProductionStateMachineException(String message) {
        super(message, logger);
    }
    
    protected ProductionStateMachineException(String message, Logger logger) {
        super(message, logger);
    }
    
}
