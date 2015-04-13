package game_engine.behaviors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of behaviors.
 * @author Tony
 *
 */
public class MultipleBehaviors implements IBehavior{
    private List<IBehavior> myBehaviors;
    
    /**
     * Constructor. Starts with an empty list of behaviors.
     */
    public MultipleBehaviors () {
        myBehaviors = new ArrayList<>();
    }
    
    /**
     * Constructor.
     * @param behaviors List of IBehavior objects.
     */
    public MultipleBehaviors (List<IBehavior> behaviors) {
        myBehaviors = behaviors;
    }
    
    /**
     * Performs each behavior.
     */
    @Override
    public void perform () {
        myBehaviors.forEach(IBehavior::perform);
    } 
    
    /**
     * Adds a behavior to the list.
     * @param behavior
     */
    public void addBehavior (IBehavior behavior) {
        myBehaviors.add(behavior);
    }

}
