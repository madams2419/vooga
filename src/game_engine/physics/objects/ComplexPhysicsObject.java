package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Material;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class ComplexPhysicsObject extends AcceleratingPhysicsObject {
	
	private Material material;
	private double mass, inverseMass;
	private double xForce, yForce;
	
	public ComplexPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hitbox, Vector position, Animation animation, Material mat) {
		super(physEng, hitbox, position, animation);
		material = mat;
		mass = computeMass();
		inverseMass = computeInverseMass();
		xForce = 0.0;
		yForce = 0.0;
	}
	
	private double computeMass() {
		return material.getDensity() * getHitbox().getArea();
	}
	
	private double computeInverseMass() {
		return mass == 0 ? 0 : 1.0/mass;
	}
	
	public void update() {
		Vector totalForce = new Vector(xForce, yForce).plus(
				getEngine().getGlobalForce()).plus(
						getEngine().getDependentForces().apply(getHitbox().getArea(), getVelocity()));
		super.increment(totalForce.times(inverseMass).times(getEngine().getTimeLapse()));
		super.update();
	}
	
	public void set(Vector amount) {
		xForce = amount.getX();
		yForce = amount.getY();
	}
	
	public void increment(Vector amount) {
		xForce += amount.getX();
		yForce += amount.getY();
	}
	
	public double getRestitution() {
		return material.getRestitution();
	}
	
	public void applyImpulse(Vector impulse) {
		super.set(impulse.times(inverseMass));
	}
}