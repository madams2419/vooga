// This entire file is part of my masterpiece.
// Brian Lavallee

package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Material;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public class ComplexPhysicsObject extends SimplePhysicsObject {
	
	private Material material;
	
	public ComplexPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hitbox, Vector position, Animation animation, Material mat) {
		super(physEng, hitbox, position, animation, 0);
		material = mat;
	}
	
	public void update(double frameRate) {
		setMass(material.getDensity() * getHitbox().getArea());
		Vector totalForce = getForce().plus(
				getEngine().getGlobalForce()).plus(
						getEngine().getDependentForces().apply(getHitbox().getArea(), getVelocity()));
		setForce(totalForce);
		super.update(frameRate);
	}
	
	public double getRestitution() {
		return material.getRestitution();
	}
	
	public void applyImpulse(Vector impulse) {
		super.applyImpulse(impulse.times(computeInverseMass()));
	}
}