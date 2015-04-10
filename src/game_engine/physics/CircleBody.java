package game_engine.physics;


public class CircleBody implements Shape {
	
	private double myRadius;
	
	public CircleBody(double radiusPixels) {
		myRadius = PhysicsEngine.pixelsToMeters(radiusPixels);
	}
	
	public double getVolume() {
		return (4/3)*Math.PI*Math.pow(myRadius, 3);
	}
	
	@Override
	public double getRadiusPixels() {
		return PhysicsEngine.metersToPixels(getRadiusMeters());
	}
	
	@Override
	public double getRadiusMeters() {
		return myRadius;
	}

}
