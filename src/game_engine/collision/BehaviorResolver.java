package game_engine.collision;

import game_engine.behaviors.IBehavior;

/**
 * 
 * @author Michael Lee
 * Simply executes behavior
 *
 */
public class BehaviorResolver implements ICollisionResolver{

	private IBehavior behavior;

	/**
	 * 
	 * @param b
	 */
	public BehaviorResolver(IBehavior b) {
		behavior = b;
	}

	@Override
	/**
	 * Execute Behavior
	 */
	public void execute() {
		behavior.perform();
	}

}
