package game_engine.objective;

import game_engine.Behavior;
import java.util.ArrayList;
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
    public void update() {
        mySubObjectives.forEach(obj -> obj.update());
        super.update();
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
    
    public static void main (String[] args) {
        Objective obj = new Objective(()-> true, Behavior.nothing());
        obj.setName("Type this");
        List<Objective> list = new ArrayList<>();
        DoRemainingObjective obj2 = new DoRemainingObjective(()-> 5, Behavior.nothing());
        obj2.setName("Collect", "Coins");
        list.add(obj2);
        list.add(obj);
        
        List<Objective> list2 = new ArrayList<>();
        Objective obj3 = new Objective(()-> false, Behavior.nothing());
        obj3.setName("nerd");
        list2.add(obj3);
        CompleteNumObjectives obj4 = new CompleteAllObjectives(list2, Behavior.nothing());
        list.add(obj4);
        System.out.println(new CompleteAllObjectives(list, Behavior.nothing()));
    }
}
