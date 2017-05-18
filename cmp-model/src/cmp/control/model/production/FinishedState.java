/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.production;

/**
 *
 * @author adrianohrl
 */
public class FinishedState extends AbstractProductionState {

    public FinishedState(ProductionStateMachineController controller) {
        super(controller);
        super.init();
    }
    
    @Override
    public void init() {}

    @Override
    public boolean equals(AbstractProductionState state) {
        return state instanceof FinishedState;
    }

    @Override
    public String toString() {
        return "FINISHED";
    }

    @Override
    public boolean isPendent() {
        return false;
    }
    
}
