package game_engine.physics_engine.physics_objects;

import game_engine.hitboxes.IHitbox;
import game_engine.physics_engine.PhysicsEngine;
import game_engine.physics_engine.Vector;
import game_engine.sprite.Animation;

public class MovingPhysicsObject extends PhysicsObject {
	
	private double xVelocity, yVelocity;
	
	public MovingPhysicsObject(PhysicsEngine physEng, IHitbox hb, Vector position, Animation animation) {
		super(physEng, hb, position, animation);
		xVelocity = 0.0;
		yVelocity = 0.0;
	}
	
	public void update() {
		super.increment(new Vector(xVelocity, yVelocity).times(getEngine().getFPS()));
		super.update();
	}
	
	public void move(Vector amount) {
		 xVelocity = amount.getX();
		 yVelocity = amount.getY();
	}
	
	public void increment(Vector amount) {
		 xVelocity += amount.getX();
		 yVelocity += amount.getY();
	}
}