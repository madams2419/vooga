package game_engine.physics;

public class Vector {
	
	private double myAngle;
	private double myMagnitude;
	
	public Vector(double angle, double magnitude) {
		myAngle = angle;
		myMagnitude = magnitude;
	}
	
	public Vector() {
		myAngle = 0;
		myMagnitude = 0;
	}
	
	public double getXMagnitude() {
		return Math.cos(myAngle) * myMagnitude;
	}
	
	public double getYMagnitude() {
		return Math.sin(myAngle) * myMagnitude;
	}
	
	public double getAngle() {
		return myAngle;
	}
	
	public double getMagnitude() {
		return myMagnitude;
	}


}
