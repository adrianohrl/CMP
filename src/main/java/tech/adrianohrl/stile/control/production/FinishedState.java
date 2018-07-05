package tech.adrianohrl.stile.control.production;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
