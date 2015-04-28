package game_engine.physics;

public class RigidBodyFactory {
	
	public RigidBody createRigidBody(double width, double height) {
		return new RectangleBody(height, width);
	}
	
	public RigidBody createRigidBody(double radius) {
		return new CircleBody(radius);
	}

}
