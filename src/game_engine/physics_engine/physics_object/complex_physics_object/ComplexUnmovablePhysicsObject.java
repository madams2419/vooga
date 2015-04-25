package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics_engine.Vector;
import game_engine.physics_engine.complex.ComplexPhysicsEngine;
//import game_engine.physics.RigidBodyFactory;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody;
import game_player.Animation;

public class ComplexUnmovablePhysicsObject extends ComplexPhysicsObject {

	public ComplexUnmovablePhysicsObject(ComplexPhysicsEngine physics,
			RigidBody rigidBody, Material material, int xPosPixels,
			int yPosPixels, Animation a) {
		super(physics, rigidBody, material, xPosPixels, yPosPixels, a);
	}

	public ComplexUnmovablePhysicsObject(ComplexPhysicsEngine physics, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels, Animation a) {
		super(physics, widthPixels,heightPixels,material,xPosPixels,yPosPixels, a);
	}

	@Override
	public void update() {
	    
	}
	
	@Override
	public void applyImpulse(Vector v) {
	    
	}
}