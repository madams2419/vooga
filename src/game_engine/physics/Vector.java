package game_engine.physics;

import java.util.List;

public class Vector {

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

	public Vector copy() {
		return new Vector(myX, myY);
	}

	public Vector plus(Vector other) {
		return new Vector(myX + other.getX(), myY + other.getY());
	}

	public Vector minus(Vector other) {
		return new Vector(myX - other.getX(), myY + other.getY());
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

	public void setX(double x) {
		myX = x;
	}

	public void setY(double y) {
		myY = y;
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

}
