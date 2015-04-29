package game_engine.physics;

public class CircleCircleCollision extends PhysicsCollision {

	private CircleBody myCircleA;
	private CircleBody myCircleB;

	public CircleCircleCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
	}
	
	protected void castRigidBodies() {
		myCircleA = (CircleBody) myObjectA.getRigidBody();
		myCircleB = (CircleBody) myObjectB.getRigidBody();
	}

	protected void solve() {
		// compute normal
		myNormal = getSeparationVector().normalize();

		// compute penetration depth
		double radiiSum = myCircleA.getRadius() + myCircleB.getRadius();
		myPenetrationDepth = radiiSum - getSeparationDistance();
	}

}


