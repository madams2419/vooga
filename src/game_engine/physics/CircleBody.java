package game_engine.physics;


public class CircleBody implements Shape {
	
	private double myRadius;
	
	public CircleBody(double radius) {
		myRadius = radius;
	}
	
	public double getVolume() {
		return (4/3)*Math.PI*Math.pow(myRadius, 3);
	}
	
	@Override
	public double getRadius() {
		return myRadius;
	}

}
