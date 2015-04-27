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
		resetBounds();
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public void update(double timeLapse) {
		incrementPosition(velocity.times(timeLapse));
		super.update(timeLapse);
	}
	
	protected void setVelocity(Vector vel) {
		vel = vel.setX(vel.getX() > maxXVelocity ? maxXVelocity : vel.getX());
		vel = vel.setX(vel.getX() < minXVelocity ? minXVelocity : vel.getX());
		vel = vel.setY(vel.getY() > maxYVelocity ? maxYVelocity : vel.getY());
		vel = vel.setY(vel.getY() < minYVelocity ? minYVelocity : vel.getY());
		velocity = vel;
		resetBounds();
	}
	
	private void resetBounds() {
		maxXVelocity = Double.POSITIVE_INFINITY;
		maxYVelocity = Double.POSITIVE_INFINITY;
		minXVelocity = Double.NEGATIVE_INFINITY;
		minYVelocity = Double.NEGATIVE_INFINITY;
	}
	
	public void setMinXVelocity(double min) {
		minXVelocity = min;
	}
	
	public void setMinYVelocity(double min) {
		minYVelocity = min;
	}
	
	public void setMaxXVelocity(double max) {
		maxXVelocity = max;
	}
	
	public void setMaxYVelocity(double max) {
		maxYVelocity = max;
	}
	
	protected void incrementVelocity(Vector amount) {
		setVelocity(velocity.plus(amount));
	}
	
	public void applyImpulse(Vector impulse) {
		incrementVelocity(impulse);
	}
}