package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	public RigidBody createRigidBody(double width, double height, String type) {
		return createRigidBody(width, height, RBodyType.valueOf(type.toUpperCase()));
	}
	
	public RigidBody createRigidBody(double width, double height, RBodyType rbType) {
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
