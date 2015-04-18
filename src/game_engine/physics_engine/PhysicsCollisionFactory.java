package game_engine.physics_engine;

import game_engine.sprite.Sprite;
import game_engine.physics.RigidBody.RBodyType;
import game_engine.physics_engine.physics_object.IPhysicsObject;

public class PhysicsCollisionFactory {

	public static PhysicsCollision getCollision(IPhysicsObject poA, IPhysicsObject poB) {
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
			return getCollision(b, a);
		}
	}

}
