package game_engine.objective;

import java.util.function.IntSupplier;
/** 
 * Objective class that is completed after some action is done a specified number of times.
 * @author Tony
 *
 */
public class DoRemainingObjective extends Objective{
    private IntSupplier myNumRemaining;

    
    /**
     * Constructor for objective representing completion for all sets of objects
     * @param numTimes Outputs the number of tasks that still need to be done.
     * @param onComplete Behavior executed when objective is complete
     * @param linked List of objectives that need to be complete before an objective is active
     */
    public DoRemainingObjective(IntSupplier numRemaining) {
        super();
        myNumRemaining = numRemaining;
        addCondition((now) -> myNumRemaining.getAsInt() <=0, Status.COMPLETE);
    }
    
    public DoRemainingObjective (int goal, IntSupplier numDone){
        this(() -> goal - numDone.getAsInt());
    }
    
    /**
     * 
     * @return Number of tasks that still need to be done.
     */
    public int getNumRemaining(){
        return Math.abs(myNumRemaining.getAsInt());
    }
    
    public void doNum(int x) {
        IntSupplier oldRemaining = myNumRemaining;
        myNumRemaining = () -> oldRemaining.getAsInt() - x;
    }
}
