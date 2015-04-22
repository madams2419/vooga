package game_engine.collisions.resolvers;

import game_engine.behaviors.IBehavior;
import game_engine.hitboxes.IHitbox;

import java.util.List;

import javafx.util.Pair;

/**
 * Determines which IBehavior to perform based on which Hitboxes are overlapping.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class HitboxResolver implements ICollisionResolver {

	private List<Pair<Integer, Integer>> pairPriorities;
	private List<IBehavior> possibleBehaviors;
	private IHitbox indicator;
	
	public HitboxResolver(List<Pair<Integer, Integer>> priorities, List<IBehavior> behaviors, IHitbox i) {
		pairPriorities = priorities;
		possibleBehaviors = behaviors;
	}
	
	public void resolveCollision() {
		List<Pair<Integer, Integer>> collidedPairs = indicator.getCollisionPairs();
		for (int i = 0; i < pairPriorities.size(); i++) {
			Pair<Integer, Integer> pair = pairPriorities.get(i);
			if (collidedPairs.contains(pair)) {
				possibleBehaviors.get(i).perform();
				return;
			}
		}
	}
}