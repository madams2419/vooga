package game_engine.physics;

public class CircleBody implements PhysicsBody {

	private double myRadius;

	public CircleBody(double radiusPixels) {
		myRadius = PhysicsEngine.pixelsToMeters(radiusPixels);
	}

	public double getVolume() {
		return (4/3)*Math.PI*Math.pow(myRadius, 3);
	}

	public double getRadiusMeters() {
		return myRadius;
	}

	public double getCxArea() {
		return Math.PI*myRadius*myRadius;
	}

}
