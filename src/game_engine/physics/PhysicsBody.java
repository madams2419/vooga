package game_engine.physics;

public abstract class PhysicsBody {

	protected double myDepth;

	public PhysicsBody(double depth) {
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
