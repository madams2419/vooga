package game_engine;

public abstract class Sprite {
	
	String name;
	int id;
	HitBox hitBox;
	PhysicsEngine physics;
	
	public Sprite() {
		// TODO
	}
	
	public abstract void update();

}
