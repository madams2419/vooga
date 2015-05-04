// This entire file is part of my masterpiece.
// Michael Adams

package game_engine.physics;

import game_engine.physics.rigidbodies.RigidBody;
import game_engine.physics.utilities.Vector;

public interface IPhysicsEngine {

	public IPhysicsEngine buildEngine();

	public void update(double timeStep);

	public void setGlobalForce(String name, Vector force);

	public void setGlobalAccel(String name, Vector accel);

	public void removeGlobalForce(String name);

	public void removeGlobalAccel(String name);

	public IPhysicsObject addPhysicsObject(RigidBody rBody, Material material, Vector position);

	public boolean isCollided(IPhysicsObject poA, IPhysicsObject poB);

}
