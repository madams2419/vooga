package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.rigidbodies.CircleBody;
import game_engine.physics.rigidbodies.RectangleBody;
import game_engine.physics.rigidbodies.RigidBody;

public class CollisionFactory {

	// TODO refactor with 2D array of suppliers
	public Collision createCollision(PhysicsObject poA, PhysicsObject poB) {
		RigidBody rbA = poA.getRigidBody();
		RigidBody rbB = poB.getRigidBody();

		if(rbA instanceof CircleBody) {
			if(rbB instanceof CircleBody) {
				return new CircleCircleCollision(poA, poB);

			} else {
				return new CircleRectCollision(poA, poB);
			}
		} else if(rbA instanceof RectangleBody) {
			if(rbB instanceof CircleBody) {
				return new CircleRectCollision(poB, poA);

			} else {
				return new RectRectCollision(poA, poB);
			}
		} else {
			return null;
		}
	}

}
