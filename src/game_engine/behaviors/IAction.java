package game_engine.behaviors;

import java.io.Serializable;


/**
 * Interface for all actions that can be executed
 * @author 
 *
 */
public interface IAction extends Serializable{
	
        /**
         * Does some action given some array of strings as parameters.
         * @param params
         */
	public void execute(String... params);
	
}
