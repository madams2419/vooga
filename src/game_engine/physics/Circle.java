package game_engine.physics;


public class Circle implements Shape {
	
	private double myRadius;
	
	public Circle(double radius) {
		myRadius = radius;
	}
	
	public double getVolume() {
		return (4/3)*Math.PI*Math.pow(myRadius, 3);
	}
	
	public double getRadius() {
		return myRadius;
	}

}
