package game_engine.physics;

public interface IPhysicsObject {

	public void addForce(double xVal, double yVal);

	public void removeForce(double xVal, double yVal);

	public void addPointingForce(double xVal, double yVal);

	public void addPointingForce(double magnitude);

	public void addVelocity(double xVal, double yVal);

	public void removeVelocity(double xVal, double yVal);

	public void applyVelocity(double xVal, double yVal);

	public double setXPixels();

	public double getXPixels();

	public double setYPixels();

	public double getYPixels();

	public double getRadiusPixels();

	public double getWidthPixels();

	public double getHeightPixels();

}


