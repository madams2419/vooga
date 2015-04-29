package game_engine.physics;

public class RigidBodyFactory {
	
	public RigidBody createRigidBody(double width, double height) {
		return new RectangleBody(width, height);
	}
	
	public RigidBody createRigidBody(double radius) {
		return new CircleBody(radius);
	}

}
