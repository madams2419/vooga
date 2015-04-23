package game_engine.physics_engine.physics_objects;

import game_engine.hitboxes.IHitbox;
import game_engine.physics_engine.PhysicsEngine;
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.complex.Material;
import game_engine.sprite.Animation;

public class ComplexPhysicsObject extends AcceleratingPhysicsObject {
	
	private Material material;
	private double mass, inverseMass;
	private double xForce, yForce;
	
	public ComplexPhysicsObject(PhysicsEngine physEng, IHitbox hitbox, Vector position, Animation animation, Material mat) {
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
		super.increment(new Vector(xForce, yForce).times(inverseMass));
		super.update();
	}
	
	public void move(Vector amount) {
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
}