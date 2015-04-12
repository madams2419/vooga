package game_engine.physics;

public class CircleBody extends RigidBody {

	private double myRadius;
	private Vector myCenter;

	public CircleBody(double radius, Vector center) {
		super();
		myRadius = radius;
		myCenter = center;
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

}
