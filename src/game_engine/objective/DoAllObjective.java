package game_engine.objective;

import game_engine.Behavior;
import java.util.List;
import java.util.function.IntSupplier;


/**
 * Objective class for handling requiring completion of all tasks of a certain nature.
 * 
 * @author Tony
 *
 */
public class DoAllObjective extends DoNTimesObjective {
    /**
     * Constructor
     * 
     * @param numTimes Number of tasks still remaining to be done.
     * @param onComplete See Objective class
     * @param linked See Objective class
     */
    public DoAllObjective (IntSupplier numTimes,
                           Behavior onComplete,
                           List<Objective> linked) {
        super(0, () -> -numTimes.getAsInt(), onComplete, linked);
    }

}
