package game_engine.physics;

import game_engine.sprite.Sprite;
import game_engine.physics.RigidBody.RBodyType;

public class PhysicsCollisionFactory {

	public static PhysicsCollision getCollision(Sprite a, Sprite b) {
		PhysicsObject poA = a.getPhysicsObject();
		PhysicsObject poB = b.getPhysicsObject();
		RigidBody rbA = poA.getRigidBody();
		RigidBody rbB = poB.getRigidBody();

		if(rbA.getType() == RBodyType.CIRCLE) {
			CircleBody circleA = (CircleBody) rbA;

			if(rbB.getType() == RBodyType.CIRCLE) {
				return new CircleCircleCollision(poA, poB, circleA, (CircleBody) rbB);

			} else {
				return new CircleRectCollision(poA, poB, circleA, (RectangleBody) rbB);
			}
		} else {
			return getCollision(b, a);
		}
	}

}
