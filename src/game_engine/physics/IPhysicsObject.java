// This entire file is part of my masterpiece.
// Michael Adams

package game_engine.physics;

import game_engine.physics.utilities.*;

public interface IPhysicsObject {
	
	public void update(double timestep);

	public void addForce(Vector vector);

	public void removeForce(Vector vector);

	public void addPointingForce(double magnitude);

	public void addVelocity(Vector vector);

	public void removeVelocity(Vector vector);

	public void applyVelocity(Vector vector);
	
	public Vector getPositionPixels();

	public void setXPixels(double pixels);

	public double getXPixels();

	public void setYPixels(double pixels);

	public double getYPixels();

	public double getRadiusPixels();

	public double getWidthPixels();

	public double getHeightPixels();
	
	public Vector getVelocity();
	
	public double getCxArea();
	
	public boolean isPositionConstrained();

}


