package game_engine;

/**
 * Interface for all actions that can be executed
 * @author 
 *
 */
public interface Behavior {

    public void execute ();

    /**
     * 
     * @return Behavior that does nothing
     */
    public static Behavior nothing () {
        return () -> {
        };
    }

    /**
     * Returns a behavior that first executes this behavior and then the after behavior.
     * 
     * @param after Behavior to be executed after this behavior is executed.
     * @return
     */
    public default Behavior andThen (Behavior after) {
        return () -> {
            this.execute();
            after.execute();
        };
    }

}
