package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public abstract class AcceleratingPhysicsObject extends MovingPhysicsObject {
	
	private Vector acceleration;
	
	public AcceleratingPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hitbox, Vector position, Animation animation) {
		super(physEng, hitbox, position, animation);
		acceleration = new Vector(0, 0);
	}
	
	public Vector getAcceleration() {
		return acceleration;
	}
	
	public void update(double timeLapse) {
		incrementVelocity(acceleration.times(timeLapse));
		super.update(timeLapse);
	}
	
	protected void setAcceleration(Vector accel) {
		 acceleration = accel;
	}
	
	protected void incrementAcceleration(Vector amount) {
		 setAcceleration(acceleration.plus(amount));
	}
}