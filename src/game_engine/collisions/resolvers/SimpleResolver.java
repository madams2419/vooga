package game_engine.collisions.resolvers;

import game_engine.behaviors.IBehavior;
import game_engine.sprite.Sprite;

/**
 * A SimpleResolver automatically triggers all of its IBehaviors when the
 * collision is detected.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class SimpleResolver implements ICollisionResolver {

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

	public void resolveCollision(Sprite spriteA, Sprite spriteB) {
		behavior.perform();
	}
}