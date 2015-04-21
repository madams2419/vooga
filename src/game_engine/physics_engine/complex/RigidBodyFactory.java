package game_engine.physics_engine.complex;

import game_engine.physics_engine.complex.ComplexPhysicsEngine;
import game_engine.physics_engine.complex.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	public static RigidBody createRigidBody(int heightPixels, int widthPixels, RBodyType rbType) {
		double height = ComplexPhysicsEngine.pixelsToMeters(heightPixels);
		double width = ComplexPhysicsEngine.pixelsToMeters(widthPixels);
		RigidBody rBody;
		
		switch (rbType) {
		case CIRCLE:
			double radius = Math.max(height, width) / 2;
			rBody = new CircleBody(radius);
			break;
		default:
			rBody = new RectangleBody(height, width);
			break;
		}
		return rBody;
	}

}
