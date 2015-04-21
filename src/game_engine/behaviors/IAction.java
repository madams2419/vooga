package game_engine.behaviors;

/**
 * Interface for all actions (particular to a piece of the game engine that can
 * influence the state of the game) that can be executed by a separate component
 * of the game engine.
 * 
 * @author Tony Qiao
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public interface IAction {

	/**
	 * Does some action given some array of strings as parameters.
	 * 
	 * @param params
	 *            params are the parameters of the method, stored as Strings so
	 *            that all IActions are interfaced the same way.
	 */
	public void execute(String... params);
}