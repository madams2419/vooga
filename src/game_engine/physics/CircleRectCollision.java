package game_engine.physics;

public class CircleRectCollision extends PhysicsCollision {

	private CircleBody myCircle;
	private RectangleBody myRectangle;
	private RectangleBody collisionRegion;

	public CircleRectCollision(PhysicsObject poA, PhysicsObject poB,
			CircleBody circle, RectangleBody rectangle) {
		super(poA, poB);
		myCircle = circle;
		myRectangle = rectangle;
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


