package game_engine;

import java.util.ArrayList;
import java.util.List;

public class MultipleBehaviors implements IBehavior{
    private List<IBehavior> myBehaviors;
    
    public MultipleBehaviors () {
        myBehaviors = new ArrayList<>();
    }
    
    public MultipleBehaviors (List<IBehavior> behaviors) {
        myBehaviors = behaviors;
    }
    
    @Override
    public void perform () {
        myBehaviors.forEach(IBehavior::perform);
    } 
    
    public void addBehavior (IBehavior behavior) {
        myBehaviors.add(behavior);
    }

}
