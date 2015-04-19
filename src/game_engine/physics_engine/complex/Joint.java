package game_engine.physics_engine.complex;

import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;

public class Joint {

	ComplexPhysicsObject myAttachment1;
	ComplexPhysicsObject myAttachment2;

	public Joint(ComplexPhysicsObject attachment1, ComplexPhysicsObject attachment2) {
		myAttachment1 = attachment1;
		myAttachment2 = attachment2;
	}

}
