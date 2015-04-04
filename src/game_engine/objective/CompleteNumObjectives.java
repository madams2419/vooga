package game_engine.objective;

import game_engine.Behavior;
import java.util.List;

public class CompleteNumObjectives extends DoRemainingObjective{
    private List<Objective> mySubObjectives;
    
    public CompleteNumObjectives (int num, List<Objective> subObjectives, Behavior onComplete) {
        super(() -> num - countCompleteObjectives(subObjectives), onComplete);
        mySubObjectives = subObjectives;
    }
    
    private static int countCompleteObjectives (List<Objective> subObjectives) {
        return (int) subObjectives.stream()
                .filter(objective -> objective.isComplete())
                .count();
    }
    
    @Override
    public void update(long now) {
        mySubObjectives.forEach(obj -> obj.update(now));
        super.update(now);
    }
    
    @Override
    public String toString() {
        super.setName("Complete", "Objectives:");
        String name = super.toString();
        for (Objective obj: mySubObjectives){
            name += tabEveryLine("\n" + obj.toString().trim());
        }
        return name;
    }
    
    private String tabEveryLine(String string) {
        return string.replaceAll("\n", "\n\t");
    }
}
