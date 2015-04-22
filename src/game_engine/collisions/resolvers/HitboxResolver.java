package game_engine.collisions.resolvers;

import game_engine.behaviors.IBehavior;
import game_engine.hitboxes.IHitbox;

import java.util.List;

/**
 * Determines which IBehavior to perform based on which Hitboxes are overlapping.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class HitboxResolver implements ICollisionResolver {

	private List<IBehavior> possibleBehaviors;
	
	private IHitbox hitboxA, hitboxB;
	
	public HitboxResolver(List<IBehavior> list, IHitbox hbA, IHitbox hbB) {
		possibleBehaviors = list;
		hitboxA = hbA;
		hitboxB = hbB;
	}
	
	public void resolveCollision() {
		
	}
}