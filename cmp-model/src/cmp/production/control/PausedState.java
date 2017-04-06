/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.control;

/**
 *
 * @author adrianohrl
 */
public class PausedState extends AbstractProductionState {

    public PausedState(ProductionStateMachineController controller) {
        super(controller);
    }
    
    @Override
    public void init() {
        super.init();
        super.add(super.controller.getRestartedState());
        super.add(super.controller.getReturnedState());
    }

    @Override
    public boolean equals(AbstractProductionState state) {
        return state instanceof PausedState;
    }

    @Override
    public String toString() {
        return "PAUSED";
    }

    @Override
    public boolean isPendent() {
        return true;
    }
    
}
