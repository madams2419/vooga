package game_engine.collisions.resolvers;

import game_engine.behaviors.IBehavior;

/**
 * A SimpleResolver automatically triggers all of its IBehaviors when the
 * collision is detected.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class SimpleResolver<T> implements ICollisionResolver<T> {

	private IBehavior behavior;

	/**
	 * Simple constructor which determines what Behavior to perform.
	 * 
	 * @param b
	 *            An IBehavior defining the behavior of a collision.
	 */
	public SimpleResolver(IBehavior b) {
		behavior = b;
	}

	public void resolveCollision(T spriteA, T spriteB) {
		behavior.perform();
	}
}