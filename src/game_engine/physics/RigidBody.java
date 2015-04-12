package game_engine.physics;

public abstract class RigidBody {

	protected double myDepth;

	public RigidBody(double depth) {
		myDepth = depth;
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();

	public double getVolume() {
		return myDepth * getArea();
	}

	public double getCxArea() {
		return myDepth * getCxLength();
	}

}
