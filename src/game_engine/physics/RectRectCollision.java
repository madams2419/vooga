package game_engine.physics;

public class RectRectCollision extends PhysicsCollision {

	private RectangleBody rectA;
	private RectangleBody rectB;

	public RectRectCollision(PhysicsObject objectA, PhysicsObject objectB) {
		super(objectA, objectB);
	}
	
	protected void castRigidBodies() {
		rectA = (RectangleBody) myObjectA.getRigidBody();
		rectB = (RectangleBody) myObjectB.getRigidBody();
	}

	// TODO refactor if statements with strategy pattern
	public void solve() {
		Vector collisionDelta = getSeparationVector();

		double halfWidthA = rectA.getWidth() / 2;
		double halfWidthB = rectB.getWidth() / 2;

		double x_overlap = halfWidthA + halfWidthB - Math.abs(collisionDelta.getX());

		double halfHeightA = rectA.getHeight() / 2;
		double halfHeightB = rectB.getHeight() / 2;

		double y_overlap = halfHeightA + halfHeightB - Math.abs(collisionDelta.getY());

		if(x_overlap < y_overlap) {
			myNormal = (collisionDelta.getX() < 0) ? Vector.WEST : Vector.EAST;
			myPenetrationDepth = x_overlap;
		} else {
			myNormal = (collisionDelta.getY() < 0) ? Vector.SOUTH : Vector.NORTH;
			myPenetrationDepth = y_overlap;
		}

	}



}


