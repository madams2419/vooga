package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	public static RigidBody createRigidBody(int heightPixles, int widthPixels, RBodyType rbType) {
		double height = PhysicsEngine.pixelsToMeters(heightPixles);
		double width = PhysicsEngine.pixelsToMeters(widthPixels);
		RigidBody rBody;
		switch (rbType) {
		case CIRCLE:
			double radius = Math.max(height, width);
			rBody = new CircleBody(radius);
			break;
		default:
			rBody = new RectangleBody(height, width);
			break;
		}
		return rBody;
	}

}
