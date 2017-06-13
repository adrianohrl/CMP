/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production;

/**
 *
 * @author adrianohrl
 */
public class RestartedState extends AbstractProductionState {

    public RestartedState(ProductionStateMachineController controller) {
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
    public boolean equals(AbstractProductionState state) {
        return state instanceof RestartedState;
    }

    @Override
    public String toString() {
        return "RESTARTED";
    }

    @Override
    public boolean isPendent() {
        return true;
    }

    @Override
    public boolean isAllowedToChangeSubordinate() {
        return true;
    }
    
}
