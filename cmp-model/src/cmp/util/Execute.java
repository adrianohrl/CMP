/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

/**
 *
 * @author adrianohrl
 */
public interface Execute<T> {
    
    public abstract void execute(Command<T> command);
    
}
