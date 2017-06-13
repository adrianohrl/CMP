/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author adrianohrl
 */
public class DAOException extends CMPException  {
    
    private static final Logger logger = Logger.getLogger(DAOException.class);

    public DAOException(String message) {
        super(message);
        logger.error(message);
    }
    
}
