/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production;

import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractProductionState {
    
    protected final ProductionStateMachineController controller;
    private ArrayList<AbstractProductionState> possibleNextStates;

    public AbstractProductionState(ProductionStateMachineController controller) {
        this.controller = controller;
    }
    
    public void init()
    {
        possibleNextStates = new ArrayList<>();
    }
    
    protected void add(AbstractProductionState possibleNextState) {
        possibleNextStates.add(possibleNextState);
    }
    
    public ArrayList<AbstractProductionState> getPossibleNextStates() {
        return possibleNextStates;
    }
    
    public boolean isValidStateTransition(AbstractProductionState nextState) {
        return possibleNextStates.contains(nextState);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractProductionState && equals((AbstractProductionState) obj);
    }
    
    public abstract boolean equals(AbstractProductionState state);
    
    @Override
    public abstract String toString();
    
    public abstract boolean isPendent();
    
    public boolean isAllowedToChangeSubordinate() {
        return false;
    }
    
}
