package game_engine.objective;

import game_engine.Behavior;
import java.util.List;
import java.util.function.IntSupplier;
/** 
 * Objective class that is completed after some action is done a specified number of times.
 * @author Tony
 *
 */
public class DoNTimesObjective extends Objective{
    
    /**
     * Constructor for doing something certain number of times
     * @param num Number of times required to perform some action
     * @param numTimes Outputs the number of times the action has been performed
     * @param onComplete Behavior executed when objective is complete
     * @param linked List of objectives that need to be complete before an objective is active
     */
    public DoNTimesObjective(int num, IntSupplier numTimes, Behavior onComplete, List<Objective> linked) {
        super(() -> numTimes.getAsInt() >= num, onComplete, linked);
    }
    
    /**
     * Constructor for objective representing completion for all sets of objects
     * @param numTimes Outputs the number of tasks that still need to be done.
     * @param onComplete Behavior executed when objective is complete
     * @param linked List of objectives that need to be complete before an objective is active
     */
    public DoNTimesObjective(IntSupplier numTimes, Behavior onComplete, List<Objective> linked) {
        this(0, () -> -numTimes.getAsInt(), onComplete, linked);
    }
}
