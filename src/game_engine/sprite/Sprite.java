package game_engine.sprite;

import game_engine.HitBox;
import game_engine.PhysicsEngine;

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
