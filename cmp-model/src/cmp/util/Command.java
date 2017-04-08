/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public interface Command<T> {
    
    public abstract void execute(T item);
    
}
