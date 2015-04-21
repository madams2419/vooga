package game_engine.physics_engine.simple;

import game_engine.physics_engine.PhysicsEngine;
import game_engine.physics_engine.physics_object.IPhysicsObject;

public class SimplePhysicsEngine extends PhysicsEngine{
	
	public SimplePhysicsEngine(){
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
