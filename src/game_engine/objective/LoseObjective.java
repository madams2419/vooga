package game_engine.objective;

import game_engine.Behavior;
import java.util.List;
import java.util.function.BooleanSupplier;

public class LoseObjective extends Objective{

    public LoseObjective (BooleanSupplier condition, Behavior onComplete, List<Objective> linked) {
        super(condition, onComplete, linked);
    }
    
    public LoseObjective (Objective objective, Behavior onComplete, List<Objective> linked) {
        super(() -> objective.isComplete(), onComplete, linked);
    }
    
    @Override
    public boolean isComplete(){
        return !super.isComplete();
    }
}
