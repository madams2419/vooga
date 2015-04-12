package game_engine.physics;

public class CircleBody extends RigidBody {

	private double myRadius;

	public CircleBody(Vector center, double radius) {
		super(center);
		myRadius = radius;
	}

	public double getArea() {
		return Math.PI * myRadius * myRadius;
	}

	public double getCxLength() {
		return getRadius();
	}

	public double getRadius() {
		return myRadius;
	}

	public boolean containsPoint(Vector point) {
		Vector centerToPoint = point.minus(myCenter);
		return centerToPoint.getMagnitude() <= myRadius;
	}

	protected void setType() {
		myType = RBodyType.CIRCLE;
	}

	protected void handleCenterChange() {
		// nothing to be done for circle bodies
	}

}
