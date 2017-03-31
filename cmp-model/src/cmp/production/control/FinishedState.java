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
public class FinishedState extends AbstractProductionState {

    public FinishedState(ProductionStateMachineController controller) {
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
