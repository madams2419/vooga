package game_engine.collisions.detectors;

import game_engine.sprite.Sprite;

/**
 * An ICollisionDetector determines whether or not two Sprites have collided. As
 * an optimization, each implementation should contain a map of Sprites to their
 * relevant collision detecting objects to shorten pre-process time.
 * 
 * @author Michael Lee
 * @since 21 April 2015
 */
public interface ICollisionDetector {

	/**
	 * Determines if two sprites are colliding.
	 * 
	 * @param spriteA
	 * 			The first involved sprite.
	 * 
	 * @param spriteB
	 * 			The second involved sprite.
	 * 
	 * @return
	 * 			True if the Sprites are deemed to be colliding and false
	 *         otherwise.
	 */
	public boolean detectCollision(Sprite spriteA, Sprite spriteB);
}