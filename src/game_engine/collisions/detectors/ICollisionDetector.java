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
	 * Determines whether or not two Sprites are colliding.
	 * 
	 * @return True if the Sprites are deemed to be colliding and false
	 *         otherwise.
	 */
	public boolean detectCollision(Sprite spriteA, Sprite spriteB);
}