package game_engine.physics;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody myCircleA;
	private CircleBody myCircleB;

	public CircleCircleCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircleA = (CircleBody) poA.getRigidBody();
		myCircleB = (CircleBody) poB.getRigidBody();
	}

	protected void solve() {
		myNormal = getSeparationVector().normalize();

		double radiiSum = myCircleA.getRadius() + myCircleB.getRadius();
		myPenetrationDepth = radiiSum - getSeparationDistance();
	}

}


