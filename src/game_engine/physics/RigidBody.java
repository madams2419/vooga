package game_engine.physics;

public abstract class RigidBody {

	//TODO move somewhere else...constants or properties file
	private static final double DEFAULT_DEPTH = 5;

	protected Vector myCenter;
	protected RBodyType myType;
	protected double myDepth;

	public enum RBodyType {
		CIRCLE, RECTANGLE
	}

	public RigidBody(Vector center) {
		this(center, DEFAULT_DEPTH);
	}

	public RigidBody(Vector center, double depth) {
		myCenter = center;
		myDepth = depth;
		setType();
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();

	protected abstract void setType();

	public abstract boolean containsPoint(Vector point);

	protected abstract void handleCenterChange();

	public void setCenter(Vector center) {
		myCenter = center;
		handleCenterChange();
	}

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
