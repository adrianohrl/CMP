/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author adrianohrl
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
