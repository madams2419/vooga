package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	private Scaler myScaler;
	
	public RigidBodyFactory(Scaler scaler) {
		myScaler = scaler;
	}
	
	public RigidBody createRigidBody(int widthPixels, int heightPixels, RBodyType rbType) {
		double height = myScaler.pixelsToMeters(heightPixels);
		double width = myScaler.pixelsToMeters(widthPixels);
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
