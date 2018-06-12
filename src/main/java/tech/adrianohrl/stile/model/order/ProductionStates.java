package tech.adrianohrl.stile.model.order;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public enum ProductionStates {
    STARTED, 
    RESTARTED, 
    PAUSED, 
    FINISHED, 
    RETURNED;
    
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
