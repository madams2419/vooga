package game_engine.behaviors;

/**
 * Interface for conditions. They have a test method which takes in no parameters and returns a
 * boolean.
 * 
 * @author Tony
 *
 */
public interface ICondition {

    /**
     * Evaluates this condition
     * @return
     */
    public boolean test ();

    /**
     * 
     * @param condition
     * @return Returns a composed condition that represents a short-circuiting logical OR of this condition and another.
     */
    public default ICondition or (ICondition condition) {
        return () -> this.test() || condition.test();
    }

    /**
     * 
     * @param condition
     * @return Returns a composed condition that represents a short-circuiting logical AND of this condition and another.
     */
    public default ICondition and (ICondition condition) {
        return () -> this.test() && condition.test();
    }
    
    /**
     * 
     * @return Returns an ICondition that represents the logical negation of this condition.
     */
    public default ICondition negate () {
        return () -> !this.test();
    }
}
