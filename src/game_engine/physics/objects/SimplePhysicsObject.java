// This entire file is part of my masterpiece.
// Brian Lavallee

package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class SimplePhysicsObject extends AcceleratingPhysicsObject {
	
	private Vector force;
	private double mass;
	
	public SimplePhysicsObject(PhysicsEngine engine, Map<String, List<IHitbox>> hitbox, Vector position, Animation animation, double m) {
		super(engine, hitbox, position, animation);
		mass = m;
		force = new Vector(0, 0);
	}
	
	protected void setMass(double m) {
		mass = m;
	}
	
	public double getMass() {
		return mass;
	}
	
	public Vector getForce() {
		return force;
	}

	protected double computeInverseMass() {
		return mass == 0 ? 0 : 1.0/mass;
	}
	
	public void update(double timeLapse) {
		setAcceleration(force.times(computeInverseMass()));
		incrementAcceleration(getEngine().getGlobalAccel().times(getMass() * computeInverseMass()));
		super.update(timeLapse);
	}
	
	protected void setForce(Vector f) {
		force = f;
	}
	
	protected void incrementForce(Vector amount) {
		setForce(force.plus(amount));
	}
	
	public void applyImpusle(Vector impulse) {
		super.applyImpulse(impulse.times(computeInverseMass()));
	}
}