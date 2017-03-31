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
public class ReturnedState extends AbstractProductionState {

    public ReturnedState(ProductionStateMachineController controller) {
        super(controller);
        super.init();
    }
    
    @Override
    public void init() {}

    @Override
    public void process(AbstractProductionState nextState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
