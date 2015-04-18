package game_engine.physics_engine.physics_object;

/**
 * physics object interface
 */
public interface IPhysicsObject {
	public abstract void update();
	public double getXPosition();
	public double getYPosition();
}
