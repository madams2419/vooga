package game_engine.physics_engine;

import java.util.List;

// Immutable 2d vector for physics computations

public class Vector {

	public static final Vector NORTH = new Vector(0, 1);
	public static final Vector SOUTH = new Vector(0, -1);
	public static final Vector EAST = new Vector(1, 0);
	public static final Vector WEST = new Vector(-1, 0);

	private double myX;
	private double myY;

	public Vector() {
		this(0, 0);
	}

	public Vector(double x, double y) {
		myX = x;
		myY = y;
	}

	public static Vector getPolarVector(double angleDeg, double magnitude) {
		double angle = Math.toRadians(angleDeg);
		double x = Math.cos(angle) * magnitude;
		double y = Math.sin(angle) * magnitude;
		return new Vector(x, y);
	}

	public static Vector sum(List<Vector> vectors) {
		Vector sum = new Vector();

		for(Vector vect : vectors) {
			sum = sum.plus(vect);
		}

		return sum;
	}

	@Override
	public boolean equals(Object other) {
		if(other == this) {
			return true;
		}

		if(!(other instanceof Vector)) {
			return false;
		}

		Vector otherVector = (Vector) other;

		return myX == otherVector.getX() && myY == otherVector.getY();
	}

	public Vector copy() {
		return new Vector(myX, myY);
	}

	public Vector plus(Vector other) {
		return new Vector(myX + other.getX(), myY + other.getY());
	}

	public Vector minus(Vector other) {
		return new Vector(myX - other.getX(), myY - other.getY());
	}

	public double dot(Vector other) {
		return myX * other.getX() + myY * other.getY();
	}

	public Vector times(double scalar) {
		return new Vector(myX * scalar, myY * scalar);
	}

	public Vector negate() {
		return new Vector(-myX, -myY);
	}

	public Vector normalize() {
		if(isZeroVector()) {
			return new Vector();
		} else {
			double mag = getMagnitude();
			return new Vector(myX / mag, myY / mag);
		}
	}

	public Vector setX(double x) {
		return new Vector(x, myY);
	}

	public Vector setY(double y) {
		return new Vector(myX, y);
	}

	public Vector incrementX(double increment) {
		return setX(myX + increment);
	}

	public Vector incrementY(double increment) {
		return setY(myY + increment);
	}

	public double getX() {
		return myX;
	}

	public double getY() {
		return myY;
	}

	public double getAngle() {
		return isZeroVector() ? 0 : Math.atan(myY/myX);
	}

	public boolean isZeroVector() {
		return (myX == 0 && myY == 0);
	}

	public double getMagnitude() {
		return Math.sqrt(myX * myX + myY * myY);
	}

	public String toString() {
		return String.format("Vector (%f, %f)\n", myX, myY);
	}

}
