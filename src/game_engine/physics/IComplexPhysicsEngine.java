// This entire file is part of my masterpiece.
// Emre Sonmez
package game_engine.physics;

public interface IComplexPhysicsEngine extends IPhysicsEngine{
	public void addGlobalForce(String name, double xVal, double yVal);

	public void addGlobalAccel(String name, double xVal, double yVal);

	public void removeGlobalForce(String name);

	public void removeGlobalAccel(String name);

	public IPhysicsObject addPhysicsObject(double xPos, double yPos, double width, double height, double density, double restitution);

}
