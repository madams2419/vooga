package game_engine.physics_engine.complex;


public class CircleBody extends RigidBody {

	private double myRadius;

	public CircleBody(double radius) {
		super();
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

	protected boolean containsPoint(Vector point) {
		return point.getMagnitude() <= myRadius;
	}

	protected void setType() {
		myType = RBodyType.CIRCLE;
	}

}
