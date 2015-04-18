package game_engine.physics_engine.physics_object;

/**
 * physics object interface
 * note: we have included multiple implementations of IPhysicsObjects (simple and complex)
 * to illustrate the flexibility of our game engine
 */
public interface IPhysicsObject {
	public abstract void update();
	public double getXPosition();
	public double getYPosition();
}
