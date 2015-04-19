package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {

	private CircleBody myCircle;
	private RectangleBody myRectangle;

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircle = (CircleBody) poA.getRigidBody();
		myRectangle = (RectangleBody) poB.getRigidBody();
	}

	//TODO handle with strategy pattern instead of if statement
	protected void solve() {
		Vector sepVector = getSeparationVector();

		// compute closest point on circle to rectangle
		Vector closestPoint = myRectangle.clampPointToEdge(sepVector);

		// check if circle center is insie rectangle
		boolean circleInsideRect = myRectangle.containsPoint(myObjectA.getPositionMeters());

		// compute raw normal vector
		Vector rawNormal = sepVector.minus(closestPoint);

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


