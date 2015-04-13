package game_engine.behaviors;
/**
 * Represents a class that has actions that can be executed.
 * @author Tony
 *
 */
public interface IActor {
    
    /**
     * Returns the action associated with the given name,
     * @param name
     * @return The action with the corresponding name.
     */
    public IAction getAction(String name);

}
