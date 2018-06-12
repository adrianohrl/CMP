package tech.adrianohrl.stile.control.model.production;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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

    @Override
    public boolean isAllowedToChangeSubordinate() {
        return true;
    }
    
}
