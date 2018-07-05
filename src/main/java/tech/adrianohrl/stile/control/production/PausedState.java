package tech.adrianohrl.stile.control.production;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
