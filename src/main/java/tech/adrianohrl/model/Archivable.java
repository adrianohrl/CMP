/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.model;

/**
 *
 * @author adrianohrl
 */
public interface Archivable {
    
    public abstract boolean isArchived();
    
    public void setArchived(boolean archived);
    
}
