package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class PhysicsCollisionFactory {

	public PhysicsCollision createCollision(PhysicsObject poA, PhysicsObject poB) {
		RigidBody rbA = poA.getRigidBody();
		RigidBody rbB = poB.getRigidBody();

		if(rbA.getType() == RBodyType.CIRCLE) {
			if(rbB.getType() == RBodyType.CIRCLE) {
				return new CircleCircleCollision(poA, poB);

			} else {
				return new CircleRectCollision(poA, poB);
			}
		} else if(rbA.getType() == RBodyType.RECTANGLE && rbB.getType() == RBodyType.RECTANGLE) {
				return new RectRectCollision(poA, poB);
		} else {
			return createCollision(poB, poA);
		}
	}

}
