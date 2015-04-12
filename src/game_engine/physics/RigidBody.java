package game_engine.physics;

public abstract class RigidBody {

	//TODO move somewhere else...constants or properties file
	private static final double DEFAULT_DEPTH = 5;

	protected double myDepth;
	protected RBodyType myType;

	public enum RBodyType {
		CIRCLE, RECTANGLE
	}

	public RigidBody() {
		this(DEFAULT_DEPTH);
	}

	public RigidBody(double depth) {
		myDepth = depth;
		setType();
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();

	protected abstract void setType();

	public abstract boolean containsPoint(Vector point);

	public RBodyType getType() {
		return myType;
	}

	public double getVolume() {
		return myDepth * getArea();
	}

	public double getCxArea() {
		return myDepth * getCxLength();
	}

}
