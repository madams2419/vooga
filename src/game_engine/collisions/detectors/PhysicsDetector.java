package game_engine.collisions.detectors;

import game_engine.physics.PhysicsEngine;
import game_engine.sprite.Sprite;

/**
 * Detects collisions based on a series of HitBoxes.  For large images, this allows
 * the designer to be more accurate than the ImageView alone without having to use
 * the costly pixel perfect detection scheme.
 * 
 * @author Brian Lavallee
 * @since 21 April 2015
 */
public class PhysicsDetector implements ICollisionDetector<Sprite> {
	
	private PhysicsEngine myPhysics;
	
	public PhysicsDetector(PhysicsEngine physics) {
		myPhysics = physics;
	}
	
	public boolean detectCollision(Sprite spriteA, Sprite spriteB) {
		return myPhysics.isCollided(spriteA.getPhysicsObject(), spriteB.getPhysicsObject());
	}

}