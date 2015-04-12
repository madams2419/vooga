package game_engine.physics;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody myCircleA;
	private CircleBody myCircleB;

	public CircleCircleCollision(PhysicsObject poA, PhysicsObject poB,
			CircleBody circleA, CircleBody circleB) {
		super(poA, poB);
		myCircleA = circleA;
		myCircleB = circleB;
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
		double radiiSum = myCircleA.getRadius() + myCircleB.getRadius();
		return radiiSum - getSeparationDistance();
	}

}


