package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics_engine.complex.ComplexPhysicsEngine;
//import game_engine.physics.RigidBodyFactory;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody;
import game_engine.physics_engine.complex.RigidBody.RBodyType;

public class ComplexMovablePhysicsObject extends ComplexPhysicsObject {

	public ComplexMovablePhysicsObject(ComplexPhysicsEngine physics,
			RigidBody rigidBody, Material material, int xPosPixels,
			int yPosPixels) {
		super(physics, rigidBody, material, xPosPixels, yPosPixels);
		// TODO Auto-generated constructor stub
	}

	public ComplexMovablePhysicsObject(ComplexPhysicsEngine physics, RBodyType rbType, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels) {
		super(physics, rbType,widthPixels,heightPixels,material,xPosPixels,yPosPixels);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		double dt = myPhysics.getTimeStep();
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel.times(dt));
		myPosition = myPosition.plus(myVelocity.times(dt));

		// temporary ground handling
		if(myPosition.getY() <= myPhysics.getGround() + myRigidBody.getRadius()) {
			myPosition = myPosition.setY(myPhysics.getGround() + myRigidBody.getRadius());
			myVelocity = myVelocity.setY(0);

		}

	}
}
