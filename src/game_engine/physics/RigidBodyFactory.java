package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

public class RigidBodyFactory {
	
	private static RigidBody createRigidBody(String imgPath, int imgHeight, int imgWidth, RBodyType rbType, Vector center) {
		RigidBody rBody;
		switch (rbType) {
		case CIRCLE:
			double radius = Math.max(node.getFitHeight(), node.getFitWidth());
			rBody = new CircleBody(radius, center);
			break;
		default:
			Vector upperLeft = new Vector(node.getX(), node.getY());
			rBody = new RectangleBody(upperLeft, node.getFitHeight(),
					node.getFitWidth());
			break;
		}
		return rBody;
	}

}
