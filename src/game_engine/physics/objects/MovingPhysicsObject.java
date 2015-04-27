package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public abstract class MovingPhysicsObject extends PhysicsObject {
	
	private Vector velocity;
	
	private double maxXVelocity, minXVelocity, maxYVelocity, minYVelocity;
	
	public MovingPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hb, Vector position, Animation animation) {
		super(physEng, hb, position, animation);
		velocity = new Vector(0 ,0);
		maxXVelocity = Double.MAX_VALUE;
		maxYVelocity = Double.MAX_VALUE;
		minXVelocity = Double.MIN_VALUE;
		minYVelocity = Double.MIN_VALUE;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public void update(double timeLapse) {
		incrementPosition(velocity.times(timeLapse));
		super.update(timeLapse);
	}
	
	protected void setVelocity(Vector vel) {
		vel.setX(vel.getX() > maxXVelocity ? maxXVelocity : vel.getX() < minXVelocity ? minXVelocity : vel.getX());
		vel.setY(vel.getY() > maxYVelocity ? maxYVelocity : vel.getY() < minYVelocity ? minYVelocity : vel.getY());
		velocity = vel;
	}
	
	public void setMinXVelocity(double min) {
		minXVelocity = min;
	}
	
	public void setMinYVelocity(double min) {
		minYVelocity = min;
	}
	
	public void setMaxXVelocity(double max) {
		minXVelocity = max;
	}
	
	public void setMaxYVelocity(double max) {
		minYVelocity = max;
	}
	
	protected void incrementVelocity(Vector amount) {
		setVelocity(velocity.plus(amount));
	}
	
	public void applyImpulse(Vector impulse) {
		incrementVelocity(impulse);
	}
}