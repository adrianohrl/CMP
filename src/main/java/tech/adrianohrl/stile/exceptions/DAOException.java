package tech.adrianohrl.stile.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class DAOException extends StileException  {
    
    private static final Logger logger = Logger.getLogger(DAOException.class);

    public DAOException(String message) {
        super(message);
        logger.error(message);
    }
    
}
