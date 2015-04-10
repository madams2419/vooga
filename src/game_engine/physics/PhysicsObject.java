package game_engine.physics;

import game_engine.HitBox;
import java.util.ArrayList;
import java.util.List;

// TODO
// - ability to run back time...some mechanism to do that

public class PhysicsObject {

	private double myInvMass;
	private Material myMaterial;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private Vector myNetInternalForce;
	private double myDirForceMagnitude;
	private List<Joint> myJoints;
	private PhysicsEngine myPhysics;
	private Shape myShape;

	public PhysicsObject(PhysicsEngine physics, Shape shape, Material material, int xPosPixels, int yPosPixels) {
		myPhysics = physics;
		myShape = shape;
		myMaterial = material;
		
		myPosition = physics.vectorPixelsToMeters(xPosPixels, yPosPixels);
		myVelocity = new Vector();
		myNetInternalForce = new Vector();
		myDirForceMagnitude = 0;
		
		myInvMass = computeInvMass();
		myAccel = computeAccel();
	}

	public void update() {
		double dt = myPhysics.getTimeStep();
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel.times(dt));
		myPosition = myPosition.plus(myVelocity.times(dt));
		
		// temporary ground handling
		if(myPosition.getY() <= myPhysics.getGround() + myShape.getRadius()) {
			myPosition.setY(myPhysics.getGround() + myShape.getRadius());
		}
	}

	private double computeInvMass() {
		double mass = myMaterial.getDensity() * myShape.getVolume();
		return 1/mass;
	}

	private Vector computeAccel() {
		Vector intNetAccel = computeNetForce().times(myInvMass);
		Vector extNetAccel = myPhysics.getNetGlobalAccel();
		return intNetAccel.plus(extNetAccel);
	}

	private Vector computeNetForce() {
		// compute internal directional force
		Vector dirForce = computeDirectionalForce();
		// get net global force
		Vector extSum = myPhysics.getNetGlobalForce();

		return myNetInternalForce.plus(dirForce).plus(extSum);
	}

	private Vector computeDirectionalForce() {
		Vector direction = myVelocity.normalize();
		return direction.times(myDirForceMagnitude);
	}

	public void addForce(Vector force) {
		myNetInternalForce.plus(force);
	}

	public void removeForce(Vector force) {
		myNetInternalForce.minus(force);
	}

	public void addDirectionalForce(double magnitude) {
		myDirForceMagnitude += magnitude;
	}

	public void removeDirectionalForce(double magnitude) {
		myDirForceMagnitude -= magnitude;
	}

	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVelocity.plus(impulse.times(myInvMass));
		setVelocity(newVelocity);
	}

	public Vector getPositionMeters() {
		return myPosition;
	}
	
	public Vector getPositionPixels() {
		return myPhysics.vectorMetersToPixels(getPositionMeters());
	}

	public void setPositionMeters(Vector positionMeters) {
		myPosition = positionMeters;
	}
	
	public void setPositionPixels(Vector positionPixels) {
		myPosition = myPhysics.vectorPixelsToMeters(positionPixels);
	}
	
	public double getXMeters() {
		return myPosition.getX();
	}
	
	public void setXMeters(double xMeters) {
		myPosition.setX(xMeters);
	}
	
	public double getYMeters() {
		return myPosition.getY();
	}
	
	public void setYMeters(double yMeters) {
		myPosition.setY(yMeters);
	}
	
	public double getXPixels() {
		return myPhysics.metersToPixels(getXMeters());
	}
	
	public void setXPixels(double xPixels) {
		myPosition.setX(myPhysics.pixelsToMeters(xPixels));
	}
	
	public double getYPixels() {
		return myPhysics.metersToPixels(getYMeters());
	}
	
	public void setYPixels(double yPixels) {
		myPosition.setY(myPhysics.pixelsToMeters(yPixels));
	}

	public Vector getVelocity() {
		return myVelocity;
	}

	public void setVelocity(Vector velocity) {
		myVelocity = velocity;
	}

	public Vector getAccel() {
		return myAccel;
	}

	public double getMass() {
		return 1/myInvMass;
	}

	public double getInvMass() {
		return myInvMass;
	}

	public void setRestitution(double restitution) {
		myMaterial.setRestitution(restitution);
	}

	public double getRestitution() {
		return myMaterial.getRestitution();
	}

	public void setMaterial(Material material) {
		myMaterial = material;
		myInvMass = computeInvMass();
	}
	
	public Shape getShape() {
		return myShape;
	}
	
	public void setShape(Shape shape) {
		myShape = shape;
		myInvMass = computeInvMass();
	}

}
