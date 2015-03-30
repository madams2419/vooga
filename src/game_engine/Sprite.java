package game_engine;
/**
 * Abstract class for the creation of multiple sprite types
 * @author 
 *
 */
public abstract class Sprite {
	
	String name;
	int id;
	HitBox hitBox;
	PhysicsEngine physics;
	
	public Sprite() {
		// TODO
	}
	/**
	 * Updates the sprite
	 */
	public abstract void update();

}
