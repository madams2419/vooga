// This entire file is part of my masterpiece.
// Tony Qiao
package game_engine.collisions.resolvers;


/**
 * Resolves a collision when it occurs by executing an IBehavior.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public interface ICollisionResolver<T> {
	
	/**
	 * Executes the behavior of a collision once it has been detected.
	 * 
	 * @param spriteA
	 * 			The first sprite involved.
	 * 
	 * @param spriteB
	 * 			The second sprite involved.
	 */
	public void resolveCollision(T spriteA, T spriteB);
}