package game_engine.objective;

import game_engine.Behavior;
import java.util.List;


public class CompleteAllObjective extends DoAllObjective {

    public CompleteAllObjective (List<Objective> subObjectives,
                                 Behavior onComplete,
                                 List<Objective> linked) {
        super( () -> (int) (subObjectives
                .stream()
                .filter(obj -> !obj.isComplete())
                .count()),
              onComplete, linked);
    }
}
