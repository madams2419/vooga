package game_engine.physics;

import game_engine.HitBox;

public class Ground extends PhysicsObject {

	public Ground(PhysicsEngine physics, Shape shape, Material material,
			HitBox hitBox, String state, int xPos, int yPos) {
		super(physics, shape, material, hitBox, state, xPos, yPos);
	}

}
