package game_engine.objective;

import game_engine.Behavior;
import java.util.List;

public class CompleteAllObjectives extends CompleteNumObjectives {

    public CompleteAllObjectives (List<Objective> subObjectives, Behavior onComplete) {
        super(subObjectives.size(), subObjectives, onComplete);
    }
    
    @Override
    public String toString(){
        return DoAllObjective.convertToAll(super.toString(), getNumRemaining());
    }

}
