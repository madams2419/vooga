package game_engine.behaviors;


/**
 * Interface for all actions that can be executed
 * @author 
 *
 */
public interface IAction {
	
        /**
         * Does some action given some array of strings as parameters.
         * @param params
         */
	public void execute(String... params);
	
}
