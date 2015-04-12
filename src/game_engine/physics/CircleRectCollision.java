package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {

	private RectangleBody rectA;
	private RectangleBody rectB;
	private RectangleBody collisionRegion;

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		rectA = (RectangleBody) poA.getRigidBody();
		rectB = (RectangleBody) poA.getRigidBody();
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
		double radiiSum = circleA.getRadius() + circleB.getRadius();
		return radiiSum - getSeparationDistance();
	}

	private RectangleBody computeCollisionRegion() {


}


