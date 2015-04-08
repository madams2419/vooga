package game_engine.physics;


public class Circle implements Shape {
	
	public double myRadius;
	
	public Circle(double radius) {
		myRadius = radius;
	}
	
	public double getVolume() {
		return (4/3)*Math.PI*Math.pow(myRadius, 3);
	}

}
