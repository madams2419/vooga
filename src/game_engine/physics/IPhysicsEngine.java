package game_engine.physics;

public interface IPhysicsEngine {

	public IPhysicsEngine buildEngine();

	public void update(double timeStep);

	public void addGlobalForce(String name, double xVal, double yVal);

	public void addGlobalAccel(String name, double xVal, double yVal);

	public void removeGlobalForce(String name);

	public void removeGlobalAccel(String name);

	public IPhysicsObject addPhysicsObject(double xPos, double yPos, double width, double height, double density, double restitution);

	public boolean isCollided(IPhysicsObject poA, IPhysicsObject poB);

}
