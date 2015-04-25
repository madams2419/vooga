package game_engine.behaviors;

/**
 * Specific implementation of an IBehavior which contains only one IAction and
 * its parameters.
 * 
 * @author Tony Qiao
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class Behavior implements IBehavior {

	private IAction myAction;
	private String[] myParams;

	/**
	 * Constructor, takes in the IAction and its parameters to fill its instance
	 * variables.
	 * 
	 * @param action
	 *            action is the IAction to be executed when the Behavior is
	 *            performed.
	 * 
	 * @param params
	 *            params are the parameters for the specified action.
	 */
	public Behavior(IAction action, String... params) {
		myAction = action;
		myParams = params;
	}

	/**
	 * Executes the associated action with the given parameters.
	 */
	public void perform() {
		myAction.execute(myParams);
	}
}