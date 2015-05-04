// This entire file is part of my masterpiece.
// Emre Sonmez
package game_engine.physics;

import game_engine.physics.utilities.Vector;

public interface IPhysicsObject {

	public void addVelocity(Vector velocity);

	public void setVelocity(Vector velocity);
	
	public void removeVelocity(Vector velocity);

	public void applyVelocity(Vector velocity);

	public void setXPixels(double xPixels);

	public double getXPixels();

	public void setYPixels(double xPixels);

	public double getYPixels();

	public double getRadiusPixels();

	public double getWidthPixels();

	public double getHeightPixels();

	public boolean isTransparent();

}


