// This entire file is part of my masterpiece.
// Emre Sonmez
package game_engine.physics;

import game_engine.physics.utilities.Vector;

public interface IComplexPhysicsObject extends IPhysicsObject {
	public void addForce(Vector force);

	public void removeForce(Vector Force);

	public void addPointingForce(double magnitude);

	public void removePointingForce(double magnitude);
}
