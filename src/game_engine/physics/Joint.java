package game_engine.physics;

public class Joint {

	PhysicsObject myAttachment1;
	PhysicsObject myAttachment2;

	public Joint(PhysicsObject attachment1, PhysicsObject attachment2) {
		myAttachment1 = attachment1;
		myAttachment2 = attachment2;
	}

}
