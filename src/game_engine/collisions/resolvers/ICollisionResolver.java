package game_engine.collisions.resolvers;

import game_engine.sprite.Sprite;

/**
 * Resolves a collision when it occurs by executing an IBehavior.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public interface ICollisionResolver {
	
	/**
	 * Executes the behavior of a collision once it has been detected.
	 * 
	 * @param spriteA
	 * 			The first sprite involved.
	 * 
	 * @param spriteB
	 * 			The second sprite involved.
	 */
	public void resolveCollision(Sprite spriteA, Sprite spriteB);
}