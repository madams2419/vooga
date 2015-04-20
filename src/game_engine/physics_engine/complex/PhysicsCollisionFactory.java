package game_engine.physics_engine.complex;

import game_engine.physics_engine.complex.RigidBody.RBodyType;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public class PhysicsCollisionFactory {

	public static PhysicsCollision getCollision(IPhysicsObject poA, IPhysicsObject poB) {
	    ComplexPhysicsObject cpoA = (ComplexPhysicsObject) poA;
	    ComplexPhysicsObject cpoB = (ComplexPhysicsObject) poB;
		RigidBody rbA = cpoA.getRigidBody();
		RigidBody rbB = cpoB.getRigidBody();

		if(rbA.getType() == RBodyType.CIRCLE) {
			if(rbB.getType() == RBodyType.CIRCLE) {
				return new CircleCircleCollision(cpoA, cpoB);

			} else {
				return new CircleRectCollision(cpoA, cpoB);
			}
		} else if(rbA.getType() == RBodyType.RECTANGLE && rbB.getType() == RBodyType.RECTANGLE) {
				return new RectRectCollision(cpoA, cpoB);
		} else {
			return getCollision(poB, poA);
		}
	}

}
