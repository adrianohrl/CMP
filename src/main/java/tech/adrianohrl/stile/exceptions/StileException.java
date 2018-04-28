/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.exceptions;

/**
 *
 * @author adrianohrl
 */
public abstract class StileException extends Exception {

    public StileException(String message) {
        super(message);
    }
    
}
