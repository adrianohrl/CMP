/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

/**
 *
 * @author adrianohrl
 */
public enum ProductionStates {
    STARTED("Início"), 
    RESTARTED("Reinício"), 
    PAUSED("Pausa"), 
    FINISHED("Término"), 
    RETURNED("Devolução");
    
    private final String str;
    
    private ProductionStates(String str) {
        this.str = str;
    }
    
    @Override
    public String toString() {
        return str;
    }
    
    public boolean isStartingState() {
        return this == STARTED || this == RESTARTED;
    }
    
    public boolean isFinishingState() {
        return this == FINISHED || this == RETURNED;
    }
    
    public boolean isFreerState() {
        return this == PAUSED || this == FINISHED || this == RETURNED;
    }
    
    public boolean isStarted() {
        return this == STARTED;
    }
    
    public boolean isRestarted() {
        return this == RESTARTED;
    }
    
    public boolean isPaused() {
        return this == PAUSED;
    }
    
    public boolean isFinished() {
        return this == FINISHED;
    }
    
    public boolean isReturned() {
        return this == RETURNED;
    }
    
    public boolean hasCasualty() {
        return this == PAUSED || this == RETURNED;
    }
    
}
