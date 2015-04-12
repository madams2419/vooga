package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {

	private CircleBody myCircle;
	private RectangleBody myRectangle;
	private RectangleBody collisionRegion;

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB) {
		super(poA, poB);
		myCircle = (CircleBody) poA.getRigidBody();
		myRectangle = (RectangleBody) poA.getRigidBody();
	}

	protected Vector computeNormal() {
		return getSeparationVector().normalize();
	}

	protected double computePenetrationDepth() {
	}

	private RectangleBody computeCollisionRegion() {
		double cRegionDepth = 0;

		if(rectA.containsPoint(rectB.getUpperLeft()) {
			return RectangleBody.getRectBodyULLR(cRegionDepth, rectB.getUpperLeft(), rectA.get



	}


}


