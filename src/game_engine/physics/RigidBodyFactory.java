package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	public static RigidBody createRigidBody(int imgHeight, int imgWidth, RBodyType rbType) {
		RigidBody rBody;
		switch (rbType) {
		case CIRCLE:
			double radius = Math.max(imgHeight, imgWidth);
			rBody = new CircleBody(radius);
			break;
		default:
			rBody = new RectangleBody(imgHeight, imgWidth);
			break;
		}
		return rBody;
	}

}
