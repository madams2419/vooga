package game_engine.physics;

import game_engine.physics.utilities.Vector;

public interface IPhysicsEngine{

	public void update(double timeStep);

	public IPhysicsObject addPhysicsObject(Vector position);

	public boolean isCollided(IPhysicsObject poA, IPhysicsObject poB);

}
