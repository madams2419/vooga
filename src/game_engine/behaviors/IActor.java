package game_engine.behaviors;

/**
 * IActors are things that can have a IAction and therefore
 * need getter methods so that the game builder can retrieve
 * and distribute the desired actions to the correct triggers.
 * 
 * @author Tony
 * @author Brian Lavallee
 * @since 20 April 2015
 */
public interface IActor {
    
    /**
     * Returns the desired IAction from the actor's set of possible
     * IAction's.
     * 
     * @param name
     * 			name is the String representation of the IAction.
     * 			This is generally specified by a preceding annotation.
     * 
     * @return
     * 		The method returns the IAction associated with the given name.
     */
    public IAction getAction(String name);
}
