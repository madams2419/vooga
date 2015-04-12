package game_engine.physics;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody circleA;
	private CircleBody circleB;

	public CircleCircleCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		circleA = (CircleBody) poA.getRigidBody();
		circleB = (CircleBody) poA.getRigidBody();
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
		double radiiSum = circleA.getRadius() + circleB.getRadius();
		return radiiSum - getSeparationDistance();
	}

}


