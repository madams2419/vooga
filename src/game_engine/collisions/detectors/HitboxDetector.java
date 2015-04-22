package game_engine.collisions.detectors;

import game_engine.hitboxes.IHitbox;
import game_engine.sprite.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Detects collisions based on a series of HitBoxes.  For large images, this allows
 * the designer to be more accurate than the ImageView alone without having to use
 * the costly pixel perfect detection scheme.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class HitboxDetector implements ICollisionDetector {
	
	private Map<Sprite, Map<String, IHitbox>> optimization;
	
	/**
	 * Creates a new HiboxDetector by setting up the optimization map.
	 */
	public HitboxDetector() {
		optimization = new HashMap<>();
	}
	
	public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
		IHitbox objectA = getHitbox(spriteA);
		IHitbox objectB = getHitbox(spriteB);
		
		return objectA.intersects(objectB);
	}
	
	/*
	 * Retrieves the correct Hitbox from the map.
	 */
	private IHitbox getHitbox(Sprite sprite) {
		if (!optimization.containsKey(sprite)) {
			optimization.put(sprite, new HashMap<>());
		}
		if (!optimization.get(sprite).containsKey(sprite.getState())) {
			optimization.get(sprite).put(sprite.getState(), sprite.getHitbox());
		}
		return optimization.get(sprite).get(sprite.getState());
	}
}