package game_engine.sprite;

import engine.SpriteBehavior;
import game_engine.Behavior;
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
	
	SpriteBehavior jump = new SpriteBehavior((lambda) -> {
	   System.out.println("jump"); 
	});
	
	Behavior b = () -> {System.out.println("jump");};
}
