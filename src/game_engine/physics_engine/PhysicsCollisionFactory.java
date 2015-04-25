package game_engine.physics_engine;

import game_engine.physics_engine.complex.RectRectCollision;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public class PhysicsCollisionFactory {

    public static IPhysicsCollision getCollision(IPhysicsObject a, IPhysicsObject b) {
	return new RectRectCollision((ComplexPhysicsObject) a, (ComplexPhysicsObject) b);
    }
}
