package game_engine.physics;

public interface PhysicsCollision {

	public PhysicsObject getPhysicsObjectA();

	public PhysicsObject getPhysicsObjectB();

	public Vector getNormal();

	public double getPenetrationDepth();

}
