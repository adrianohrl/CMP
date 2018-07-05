package tech.adrianohrl.stile.control.production;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
