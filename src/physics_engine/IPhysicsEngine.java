package physics_engine;

/**
 * physics engine interface
 * contains update, add, remove, and resolve collision method definitions
 *
 */
public interface IPhysicsEngine {
	public void update();
	public void addPhysicsObject(IPhysicsObject physicsObject);
	public void removePhysicsObject(IPhysicsObject physicsObject);
	public void resolveCollision(IPhysicsObject physicsObject1, IPhysicsObject physicsObject2);
}
