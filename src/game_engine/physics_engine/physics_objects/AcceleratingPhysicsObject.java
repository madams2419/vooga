package game_engine.physics_engine.physics_objects;

import game_engine.hitboxes.IHitbox;
import game_engine.physics_engine.PhysicsEngine;
import game_engine.physics_engine.Vector;
import game_engine.sprite.Animation;

public class AcceleratingPhysicsObject extends MovingPhysicsObject {
	
	private double xAccel, yAccel;
	
	public AcceleratingPhysicsObject(PhysicsEngine physEng, IHitbox hitbox, Vector position, Animation animation) {
		super(physEng, hitbox, position, animation);
		xAccel = 0.0;
		yAccel = 0.0;
	}
	
	public void update() {
		super.increment(new Vector(xAccel, yAccel).times(getEngine().getFPS()));
		super.update();
	}
	
	public void move(Vector amount) {
		 xAccel = amount.getX();
		 yAccel = amount.getY();
	}
	
	public void increment(Vector amount) {
		 xAccel += amount.getX();
		 yAccel += amount.getY();
	}
}