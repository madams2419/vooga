package game_engine.objective;

import game_engine.Behavior;
import groovy.util.Eval;
import java.util.ArrayList;
import java.util.List;


public class CompleteNumObjective extends DoNTimesObjective {
    private List<Objective> mySubObjectives;
    
    public CompleteNumObjective (int num, List<Objective> subObjectives, Behavior onComplete, List<Objective> linked) {
        super(num, () -> getNumComplete(subObjectives), onComplete, linked);
        mySubObjectives = subObjectives;
    }
    
    public CompleteNumObjective(List<Objective> subObjectives, Behavior onComplete, List<Objective> linked){
        super(()-> subObjectives.size() - getNumComplete(subObjectives), onComplete, linked);
    }
    
    private static int getNumComplete(List<Objective> subObjectives){
        return (int) subObjectives.stream().filter(objective -> objective.isComplete()).count();
    }
    
    @Override
    public void update() {
        mySubObjectives.forEach(objective -> objective.update());
        super.update();
    }
}
