package tech.adrianohrl.stile.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionException extends StileException {
    
    private static final Logger logger = Logger.getLogger(ProductionException.class);

    public ProductionException(String message) {
        this(message, logger);
    }
    
    protected ProductionException(String message, Logger logger) {
        super(message);
        logger.fatal(message);
    }
    
}
