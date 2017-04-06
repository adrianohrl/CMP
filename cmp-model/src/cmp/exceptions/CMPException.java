/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.exceptions;

/**
 *
 * @author adrianohrl
 */
public abstract class CMPException extends Exception {

    public CMPException(String message) {
        super(message);
    }
    
}
