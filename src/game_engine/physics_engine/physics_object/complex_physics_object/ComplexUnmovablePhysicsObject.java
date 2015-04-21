package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics_engine.complex.ComplexPhysicsEngine;
//import game_engine.physics.RigidBodyFactory;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody;
import game_engine.physics_engine.complex.RigidBody.RBodyType;

public class ComplexUnmovablePhysicsObject extends ComplexPhysicsObject {

	public ComplexUnmovablePhysicsObject(ComplexPhysicsEngine physics,
			RigidBody rigidBody, Material material, int xPosPixels,
			int yPosPixels) {
		super(physics, rigidBody, material, xPosPixels, yPosPixels);
	}

	public ComplexUnmovablePhysicsObject(ComplexPhysicsEngine physics, RBodyType rbType, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels) {
		super(physics, rbType,widthPixels,heightPixels,material,xPosPixels,yPosPixels);
	}

	@Override
	public void update() {

	}

}
