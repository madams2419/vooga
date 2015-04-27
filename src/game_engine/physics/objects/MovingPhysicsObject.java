package game_engine.physics.objects;

import java.util.List;
import java.util.Map;

import game_engine.hitboxes.IHitbox;
import game_engine.physics.Vector;
import game_engine.physics.engines.PhysicsEngine;
import game_engine.sprite.Animation;

public abstract class MovingPhysicsObject extends PhysicsObject {
	
	private Vector totalVelocity;
	private Vector controlVelocity;
	
	private double maxMajorVelocity, minMajorVelocity, maxMinorVelocity, minMinorVelocity;
	private Vector majorAxis, minorAxis;
	
	public MovingPhysicsObject(PhysicsEngine physEng, Map<String, List<IHitbox>> hb, Vector position, Animation animation) {
		super(physEng, hb, position, animation);
		totalVelocity = new Vector(0, 0);
		controlVelocity = new Vector(0, 0);
		resetBounds();
	}
	
	public Vector getVelocity() {
		return totalVelocity;
	}
	
	public void update(double timeLapse) {
		incrementPosition(totalVelocity.times(timeLapse));
		super.update(timeLapse);
	}
	
	protected void setVelocity(Vector vel) {
		Vector majorComponent = majorAxis.times(majorAxis.dot(vel) / Math.pow(majorAxis.getMagnitude(), 2));
		Vector minorComponent = minorAxis.times(minorAxis.dot(vel) / Math.pow(minorAxis.getMagnitude(), 2));
		
		double sign = majorAxis.dot(majorComponent) / Math.abs(majorAxis.dot(majorComponent));
		double magnitude = majorComponent.getMagnitude();
		double inverseMagnitude = magnitude == 0 ? 0 : 1.0/magnitude;
		majorComponent = majorComponent.times(sign * majorComponent.getMagnitude() > maxMajorVelocity ? maxMajorVelocity : 
											  sign * majorComponent.getMagnitude() < minMajorVelocity ? minMajorVelocity : majorComponent.getMagnitude());
		majorComponent = majorComponent.times(inverseMagnitude);
		
		sign = minorAxis.dot(minorComponent) / Math.abs(minorAxis.dot(minorComponent));
		magnitude = minorComponent.getMagnitude();
		inverseMagnitude = magnitude == 0 ? 0 : 1.0/magnitude;
		minorComponent = minorComponent.times(sign * minorComponent.getMagnitude() > maxMinorVelocity ? maxMinorVelocity : 
											  sign * minorComponent.getMagnitude() < minMinorVelocity ? minMinorVelocity : minorComponent.getMagnitude());
		minorComponent = minorComponent.times(inverseMagnitude);
		
		totalVelocity = majorComponent.plus(minorComponent);
		
		resetBounds();
	}
	
	private void resetBounds() {
		maxMajorVelocity = Double.POSITIVE_INFINITY;
		maxMinorVelocity = Double.POSITIVE_INFINITY;
		minMajorVelocity = Double.NEGATIVE_INFINITY;
		minMinorVelocity = Double.NEGATIVE_INFINITY;
		majorAxis = new Vector(1, 0);
		minorAxis = new Vector(0, 1);
	}
	
	public void setMajorAxis(Vector dir) {
		majorAxis = dir;
	}
	
	public void setMinorAxis(Vector dir) {
		minorAxis = dir;
	}
	
	public void setMinMajorVelocity(double min) {
		minMajorVelocity = min;
	}
	
	public void setMinMinorVelocity(double min) {
		minMinorVelocity = min;
	}
	
	public void setMaxMajorVelocity(double max) {
		maxMajorVelocity = max;
	}
	
	public void setMaxMinorVelocity(double max) {
		maxMinorVelocity = max;
	}
	
	protected void incrementVelocity(Vector amount) {
		setVelocity(totalVelocity.plus(amount));
	}
	
	public void applyImpulse(Vector impulse) {
		incrementVelocity(impulse);
	}
	
	public void applyControlImpulse(Vector impulse) {
		Vector realImpulse = impulse.copy();
		
		if (Math.abs(totalVelocity.getX() - controlVelocity.getX()) > Math.abs(totalVelocity.getX() - controlVelocity.plus(impulse).getX())) {
			realImpulse = new Vector(0, realImpulse.getY());
		}
		
		if (Math.abs(totalVelocity.minus(controlVelocity).getX()) > Math.abs(totalVelocity.minus(controlVelocity.plus(impulse)).getX())) {
			realImpulse = new Vector(realImpulse.getX(), 0);
		}
		controlVelocity = controlVelocity.plus(impulse);
		applyImpulse(realImpulse);
	}
}