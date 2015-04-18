package game_engine.physics_engine.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.sprite.Sprite;

/**
 * complex physics engine implementation
 *
 */
public class ComplexPhysicsEngine extends PhysicsEngine{
	
	PhysicsCollisionFactory collisionFactory;
	
	public ComplexPhysicsEngine(){
		super();
	}
	
	@Override
	public void update() {
		getPhysicsObjects().forEach(physicsObject->physicsObject.update());
	}

	@Override
	public void resolveCollision(IPhysicsObject physicsObject1,
			IPhysicsObject physicsObject2) {
		// TODO resolve collisions
	}

}
