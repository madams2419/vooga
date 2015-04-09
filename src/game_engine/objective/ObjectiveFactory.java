package game_engine.objective;

import game_engine.IBehavior;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

public class ObjectiveFactory {

    public Objective makeObjective (Predicate<Long> condition, IBehavior behavior) {
        return new Objective(condition, behavior);
    }
    
    public Objective makeDoNumObjective (int goal, IntSupplier numDone, IBehavior behavior) {
        return makeDoAllObjective(() -> goal - numDone.getAsInt(), behavior);
    }
    
    public Objective makeDoAllObjective(IntSupplier numRemaining, IBehavior behavior){
        return new DoRemainingObjective(numRemaining, behavior);
    }
    
    public Objective makeCompleteSubObjectives(int goal, List<Objective> subObjectives, IBehavior behavior) {
        return new CompleteNumObjectives(goal, subObjectives, behavior);
    }
}
