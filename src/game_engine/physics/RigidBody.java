package game_engine.physics;

public abstract class RigidBody {

	protected RBodyType myType;
	protected double myDepth;

	public enum RBodyType {
		CIRCLE, RECTANGLE
	}

	public RigidBody() {
		this(Constants.DEPTH);
	}

	public RigidBody(double depth) {
		myDepth = depth;
		setType();
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();
	
	public abstract double getHeight();
	
	public abstract double getWidth();

	protected abstract void setType();

	protected abstract boolean containsPoint(Vector point);

	public boolean containsPoint(Vector center, Vector point) {
		Vector normalizedPoint = point.minus(center);
		return containsPoint(normalizedPoint);
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
