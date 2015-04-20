package game_engine.behaviors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Wraps an IAction with its parameters.
 * @author 
 *
 */
public class Behavior implements IBehavior{
	private IAction myAction;
	private String[] myParams;
    
        /**
         * Constructor
         * @param action
         * @param params Parameters for the specified action.
         */
	public Behavior (IAction action, String... params) {
	    myAction = action;
	    myParams = params;
	}
	
	/**
	 * Executes the associated action with the given parameters. 
	 */
	public void perform () {
	    myAction.execute(myParams);
	}
}
