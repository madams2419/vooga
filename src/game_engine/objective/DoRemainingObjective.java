package game_engine.objective;

import game_engine.Behavior;
import java.util.function.IntSupplier;
/** 
 * Objective class that is completed after some action is done a specified number of times.
 * @author Tony
 *
 */
public class DoRemainingObjective extends Objective{
    private IntSupplier myNumRemaining;
    private String myTask;
    private String myTarget;
    
    /**
     * Constructor for objective representing completion for all sets of objects
     * @param numTimes Outputs the number of tasks that still need to be done.
     * @param onComplete Behavior executed when objective is complete
     * @param linked List of objectives that need to be complete before an objective is active
     */
    public DoRemainingObjective(IntSupplier numRemaining, Behavior onComplete) {
        super((now)-> numRemaining.getAsInt() <= 0, onComplete);
        myNumRemaining = numRemaining;
    }
    
    /**
     * 
     * @return Number of tasks that still need to be done.
     */
    public int getNumRemaining(){
        return Math.abs(myNumRemaining.getAsInt());
    }
    
    public void setName (String task, String target) {
        myTask = task;
        myTarget = target;
    }
    
    @Override
    public String toString() {
        return myTask + " " + getNumRemaining() + " " + myTarget;
    }
}
