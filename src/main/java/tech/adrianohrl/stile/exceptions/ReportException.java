package tech.adrianohrl.stile.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ReportException extends StileException {
    
    private static final Logger logger = Logger.getLogger(ReportException.class);

    public ReportException(String message) {
        super(message);
        logger.warn(message);
    }
    
}
