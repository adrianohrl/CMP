package tech.adrianohrl.stile.control.production;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
