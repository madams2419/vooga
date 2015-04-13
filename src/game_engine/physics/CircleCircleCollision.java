package game_engine.physics;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody myCircleA;
	private CircleBody myCircleB;

	public CircleCircleCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircleA = (CircleBody) poA.getRigidBody();
		myCircleB = (CircleBody) poB.getRigidBody();
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
		double radiiSum = myCircleA.getRadius() + myCircleB.getRadius();
		double test =  radiiSum - getSeparationDistance();
		getSeparationDistance();
		return test;
	}

}


