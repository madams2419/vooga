package game_engine.behaviors;

import java.util.ArrayList;
import java.util.List;

/**
 * Another implementation of IBehavior which demonstrates the composite pattern.
 * Executes a list of IActions as opposed to just one.
 * 
 * @author Tony Qiao
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class MultipleBehaviors implements IBehavior {

	private List<IBehavior> myBehaviors;

	/**
	 * Constructor. Starts with an empty list of Behaviors which will be
	 * populated one by one.
	 */
	public MultipleBehaviors() {
		myBehaviors = new ArrayList<>();
	}

	/**
	 * Constructor. Starts with a pre-constructed list of Behaviors.
	 * 
	 * @param behaviors
	 *            behaviors is the List of Behaviors constructed elsewhere in
	 *            the code.
	 */
	public MultipleBehaviors(List<IBehavior> behaviors) {
		myBehaviors = behaviors;
	}

	/**
	 * Performs each behavior in the list.
	 */
	public void perform() {
		myBehaviors.forEach(IBehavior::perform);
	}

	/**
	 * Adds a behavior to the list.
	 * 
	 * @param behavior
	 *            behavior is an IBehavior (potentially a list) to be performed
	 *            on perform.
	 */
	public void addBehavior(IBehavior behavior) {
		myBehaviors.add(behavior);
	}
}