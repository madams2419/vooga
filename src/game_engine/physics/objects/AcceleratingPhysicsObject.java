package game_engine.physics.objects;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class AcceleratingPhysicsObject extends MovingPhysicsObject {
	
	private double xAccel, yAccel;
	
	public AcceleratingPhysicsObject(PhysicsEngine physEng, IHitbox hitbox, Vector position, Animation animation) {
		super(physEng, hitbox, position, animation);
		xAccel = 0.0;
		yAccel = 0.0;
	}
	
	public void update() {
		Vector totalAccel = new Vector(xAccel, yAccel).plus(getEngine().getGlobalAccel());
		super.increment(totalAccel.times(getEngine().getTimeLapse()));
		super.update();
	}
	
	public void set(Vector amount) {
		 xAccel = amount.getX();
		 yAccel = amount.getY();
	}
	
	public void increment(Vector amount) {
		 xAccel += amount.getX();
		 yAccel += amount.getY();
	}
	
	public void applyImpulse(Vector impulse) {
		super.set(impulse);
	}
}