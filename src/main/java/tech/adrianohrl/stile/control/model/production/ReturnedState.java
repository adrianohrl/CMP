/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production;

/**
 *
 * @author adrianohrl
 */
public class ReturnedState extends AbstractProductionState {

    public ReturnedState(ProductionStateMachineController controller) {
        super(controller);
        super.init();
    }
    
    @Override
    public void init() {}

    @Override
    public boolean equals(AbstractProductionState state) {
        return state instanceof ReturnedState;
    }

    @Override
    public String toString() {
        return "RETURNED";
    }

    @Override
    public boolean isPendent() {
        return false;
    }
    
}
