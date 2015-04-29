package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.rigidbodies.CircleBody;
import game_engine.physics.rigidbodies.RectangleBody;
import game_engine.physics.utilities.Vector;

public class CircleRectCollision extends Collision {

	private CircleBody myCircle;
	private RectangleBody myRectangle;

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
	}
	
	protected void castRigidBodies() {
		myCircle = (CircleBody) myObjectA.getRigidBody();
		myRectangle = (RectangleBody) myObjectB.getRigidBody();
	}

	//TODO refactor if statements with strategy pattern
	protected void solve() {
		Vector collisionDelta = getSeparationVector();

		// compute closest point on circle to rectangle
		Vector closestPoint = myRectangle.clampPointToEdge(collisionDelta);

		// check if circle center is insie rectangle
		boolean circleInsideRect = myRectangle.containsPoint(myObjectA.getPositionMeters());

		// compute raw normal vector
		Vector rawNormal = collisionDelta.minus(closestPoint);

		// compute penetration depth and unit normal depending on collision type
		if(circleInsideRect) {
			myNormal = rawNormal.normalize().negate();
			myPenetrationDepth = myCircle.getRadius() + rawNormal.getMagnitude();
		} else {
			myNormal = rawNormal.normalize();
			myPenetrationDepth = myCircle.getRadius() - rawNormal.getMagnitude();
		}

	}

}


