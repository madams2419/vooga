// This entire file is part of my masterpiece.
// Brian Lavallee

package game_engine.collisions.resolvers;

import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Sprite;

public class PhysicalResolver implements ICollisionResolver {

	private PhysicsEngine engine;
	
	public PhysicalResolver(PhysicsEngine e) {
		engine = e;
	}
	
	public void resolveCollision(Sprite spriteA, Sprite spriteB) {
		engine.resolveCollision(spriteA.getPhysicsObject(), spriteB.getPhysicsObject());
	}
}