package game_engine.collisions.resolvers;

/**
 * Resolves a collision when it occurs by executing an IBehavior.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public interface ICollisionResolver {
	
	/**
	 * Method which handles executing the IBehavior for the resolver.
	 */
	public void resolveCollision();
}