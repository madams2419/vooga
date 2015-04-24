package game_engine.objectives;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;


public class CompleteNumObjectives extends DoRemainingObjective {
    private List<Objective> mySubObjectives;

    public CompleteNumObjectives (int num, List<Objective> subObjectives) {
        super( () -> num - countObjectives(subObjectives, obj -> obj.isComplete()));
        addCondition( (now) -> getNumRemaining() >= countObjectives(subObjectives,
                                                                    obj -> !obj.isFinished()),
                     Status.FAILED);
        mySubObjectives = subObjectives;
    }
    
    public CompleteNumObjectives (List<Objective> subObjectives) {
        super(() -> countObjectives(subObjectives, obj -> obj.isComplete()));
        addCondition((now) -> countObjectives(subObjectives, obj -> obj.isFailed()) >0, Status.FAILED);
    }

    private static int countObjectives (List<Objective> subObjectives,
                                        Predicate<Objective> predicate) {
        return (int) subObjectives.stream()
                .filter(predicate)
                .count();
    }

    @Override
    public void update (double now) {
        // needed?
        mySubObjectives.forEach(obj -> obj.update(now));
        super.update(now);
    }
    
    public List<Objective> getSubObjectives() {
        return Collections.unmodifiableList(mySubObjectives);
    }
}
