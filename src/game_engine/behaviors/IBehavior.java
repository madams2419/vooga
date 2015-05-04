// This entire file is part of my masterpiece.
// Tony Qiao
package game_engine.behaviors;

/**
 * Acts as a wrapper for an IAction and its parameters. This interface allows
 * for the composite pattern. This prevents the receivers of IActions from
 * either selectively executing only certain IAction or from changing the
 * parameters of the IActions.
 * 
 * @author Tony Qiao
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public interface IBehavior {

	/**
	 * Executes the IAction by passing the String[] params into the method.
	 */
	public void perform();
    
    public default IBehavior andThen (IBehavior behavior) {
        return () -> {
            this.perform();
            behavior.perform();
        };
    }
}