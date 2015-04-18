package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {

	private final static Vector NORTH_NORMAL = new Vector(0, 1);
	private final static Vector WEST_NORMAL = new Vector(-1, 0);
	private final static Vector EAST_NORMAL = new Vector(1, 0);
	private final static Vector SOUTH_NORMAL = new Vector(-1, 0);

	private CircleBody myCircle;
	private RectangleBody myRectangle;
	private double cWidth;
	private double cHeight;
	private CollisionType cType;

	private enum CollisionType {
		B_UPPER_LEFT, B_UPPER_RIGHT, B_LOWER_LEFT, B_LOWER_RIGHT, NONE
	}

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircle = (CircleBody) poA.getRigidBody();
		myRectangle = (RectangleBody) poB.getRigidBody();
	}

	protected void solve() {
		Vector sep = getSeperationVector();
		Vector rHalf = myRectangle.getUpperRight();

		Vector closest = computeClosest();

	}

	private Vector computeClosest() {
		Vector sep = getSeperationVector();


}


