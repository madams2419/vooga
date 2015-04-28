package game_engine.physics;

public class PhysicsCollisionFactory {

	// TODO refactor with 2D array of suppliers
	public PhysicsCollision createCollision(PhysicsObject poA, PhysicsObject poB) {
		RigidBody rbA = poA.getRigidBody();
		RigidBody rbB = poB.getRigidBody();

		if(rbA instanceof CircleBody) {
			if(rbB instanceof CircleBody) {
				return new CircleCircleCollision(poA, poB);

			} else {
				return new CircleRectCollision(poA, poB);
			}
		} else if(rbA instanceof RectangleBody) {
			if(rbB instanceof CircleBody) {
				return new CircleRectCollision(poB, poA);

			} else {
				return new RectRectCollision(poA, poB);
			}
		} else {
			return null;
		}
	}

}
