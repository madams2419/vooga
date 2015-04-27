package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class MovingPhysicsObject extends PhysicsObject {
	
	private double xVelocity, yVelocity;
	
	public MovingPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hb, Vector position, Animation animation) {
		super(physEng, hb, position, animation);
		xVelocity = 0.0;
		yVelocity = 0.0;
	}
	
	public Vector getVelocity() {
		return new Vector(xVelocity, yVelocity);
	}
	
	public void update(double frameRate) {
		super.increment(new Vector(xVelocity, yVelocity).times(frameRate));
		super.update(frameRate);
	}
	
	public void set(Vector amount) {
		 xVelocity = amount.getX();
		 yVelocity = amount.getY();
	}
	
	public void increment(Vector amount) {
		 xVelocity += amount.getX();
		 yVelocity += amount.getY();
	}
	
	public void applyImpulse(Vector impulse) {
		set(impulse);
	}

	@Override
	public double getRestitution() {
		return 0;
	}
}