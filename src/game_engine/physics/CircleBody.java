package game_engine.physics;

public class CircleBody extends PhysicsBody {

	private double myRadius;

	public CircleBody(double depth, double radius) {
		super(depth);
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

}
