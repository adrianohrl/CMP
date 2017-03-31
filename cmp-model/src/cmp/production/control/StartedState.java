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
public class StartedState extends AbstractProductionState {

    public StartedState(ProductionStateMachineController controller) {
        super(controller);
    }

    @Override
    public void init() {
        super.init();
        super.add(super.controller.getPausedState());
        super.add(super.controller.getFinishedState());
        super.add(super.controller.getReturnedState());
    }

    @Override
    public void process(AbstractProductionState nextState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(AbstractProductionState state) {
        return state instanceof StartedState;
    }

    @Override
    public String toString() {
        return "STARTED";
    }

    @Override
    public boolean isPendent() {
        return true;
    }
    
}
