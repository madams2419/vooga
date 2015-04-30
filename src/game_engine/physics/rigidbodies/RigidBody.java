package game_engine.physics.rigidbodies;

import game_engine.physics.utilities.Constants;
import game_engine.physics.utilities.Vector;

public abstract class RigidBody {
	
	public static final RigidBody POINT = new CircleBody(0);
	protected double myDepth;

	public RigidBody() {
		this(Constants.DEPTH);
	}

	public RigidBody(double depth) {
		myDepth = depth;
	}

	public abstract double getArea();

	public abstract double getCxLength();

	public abstract double getRadius();
	
	public abstract double getHeight();
	
	public abstract double getWidth();

	protected abstract boolean containsPoint(Vector point);

	public boolean containsPoint(Vector center, Vector point) {
		Vector normalizedPoint = point.minus(center);
		return containsPoint(normalizedPoint);
	}

	public double getVolume() {
		return myDepth * getArea();
	}

	public double getCxArea() {
		return myDepth * getCxLength();
	}

}
