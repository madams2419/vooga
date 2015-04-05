package game_engine.physics;

import java.util.Map;
import java.util.HashMap;

import game_engine.sprite.Sprite;

public class PhysicsEngine {

	private static double GRAV_DIRECTION = -90;

	private Map<String, Vector> myGlobalForces;

	public PhysicsEngine(double gravity) {
		myGlobalForces = new HashMap<>();

		Vector gravVector = new Vector(GRAV_DIRECTION, gravity);
		myGlobalForces.put("gravity", gravVector);
	}

	public void resolveCollision(PhysicsObject a, PhysicsObject b) {
		Vector relVel = b.getVelocity().subtract

	}

	public void resolveCollision(Sprite a, Sprite b) {
		resolveCollision(a.getPhysicsObject(), b.getPhysicsObject());
	}

}
