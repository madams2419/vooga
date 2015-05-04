// This entire file is part of my masterpiece.
// Emre Sonmez

package game_engine.physics;

import game_engine.physics.rigidbodies.RigidBody;
import game_engine.physics.utilities.Scaler;
import game_engine.physics.utilities.Vector;

import java.util.Observable;
import java.util.function.Supplier;

public class PhysicsObject extends Observable implements IComplexPhysicsObject{

	private double myInvMass;
	private Material myMaterial;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private Vector myNetInternalForce;
	private double myDirForceMagnitude;
	private PhysicsEngine myPhysics;
	private Scaler myScaler;
	private Supplier<Vector> myPositionConstraint;
	private RigidBody myRigidBody;

	public PhysicsObject(PhysicsEngine physics, RigidBody rigidBody, Material material, Vector positionPx) {
		myPhysics = physics;
		myScaler = physics.getScaler();
		myRigidBody = rigidBody;
		myMaterial = material;

		myPosition = myScaler.vectorPixelsToMeters(positionPx);
		
		myVelocity = Vector.ZERO;
		myNetInternalForce = Vector.ZERO;
		myDirForceMagnitude = 0;

		myInvMass = computeInvMass();
		myAccel = computeAccel();

		myPositionConstraint = null;
		System.out.println(myPosition);
	}

	public void update(double dt) {
		if(myPositionConstraint == null) {
			myAccel = computeAccel();
			myVelocity = myVelocity.plus(myAccel.times(dt));
			myPosition = myPosition.plus(myVelocity.times(dt));
		} else {
			myAccel = Vector.ZERO;
			myVelocity = Vector.ZERO;
			myPosition = myPositionConstraint.get();
		}
		
		setChanged();
		notifyObservers();
	}

	public void constrainPosition(Supplier<Vector> positionConstraint) {
		myPositionConstraint = positionConstraint;
	}

	private double computeInvMass() {
		return (computeMass() == 0) ? 0 : 1/computeMass();
	}

	private double computeMass() {
		return myMaterial.getDensity() * myRigidBody.getVolume();
	}

	private Vector computeAccel() {
		if(myInvMass == 0) {
			return new Vector(0,0);
		}
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
		Vector intForce = direction.times(myDirForceMagnitude);
		return intForce.plus(myPhysics.getDragForce(this));
	}

	//TODO refactor so apply and add velocity have different behaviors
	public void applyVelocity(Vector velocity) {
		addVelocity(velocity);
	}

	public void addVelocity(Vector velocity) {
		Vector newVelocity = myVelocity.plus(velocity);
		setVelocity(newVelocity);
	}

	public void addPosition(Vector position) {
		Vector newPosition = myPosition.plus(position);
		setPosition(newPosition);
	}

	public void setPosition(Vector newPosition) {
		myPosition = newPosition;
	}

	public Vector getPositionMeters() {
		return myPosition;
	}

	public Vector getPositionPixels() {
		return myScaler.vectorMetersToPixels(getPositionMeters());
	}

	public double getXMeters() {
		return myPosition.getX();
	}

	public void setXMeters(double xMeters) {
		myPosition = myPosition.setX(xMeters);
	}

	public double getYMeters() {
		return myPosition.getY();
	}

	public void setYMeters(double yMeters) {
		myPosition = myPosition.setY(yMeters);
	}

	public double getXPixels() {
		return myScaler.metersToPixels(getXMeters());
	}

	public void setXPixels(double xPixels) {
		myPosition = myPosition.setX(myScaler.pixelsToMeters(xPixels));
	}

	public double getYPixels() {
		return myScaler.metersToPixels(getYMeters());
	}

	public void setYPixels(double yPixels) {
		myPosition = myPosition.setY(myScaler.pixelsToMeters(yPixels));
	}

	public double getRadiusPixels() {
		return myScaler.metersToPixels(myRigidBody.getRadius());
	}

	public double getWidthPixels() {
		return myScaler.metersToPixels(myRigidBody.getWidth());
	}

	public double getHeightPixels() {
		return myScaler.metersToPixels(myRigidBody.getHeight());
	}

	public Vector getVelocity() {
		return myVelocity;
	}

	public void setVelocity(Vector velocity) {
		myVelocity = velocity;
	}
	
	public void removeVelocity(Vector velocity) {
		double newX = (Math.abs(myVelocity.getX()) < Math.abs(velocity.getX())) ? 0 : myVelocity.getX() - velocity.getX();
		double newY = (Math.abs(myVelocity.getY()) < Math.abs(velocity.getY())) ? 0 : myVelocity.getY() - velocity.getY();
		myVelocity = new Vector(newX, newY);
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

	public double getRestitution() {
		return myMaterial.getRestitution();
	}

	public double getStaticFriction() {
		return myMaterial.getStaticFriction();
	}

	public double getKineticFriction() {
		return myMaterial.getKineticFriction();
	}

	public void setMaterial(Material material) {
		myMaterial = material;
		myInvMass = computeInvMass();
	}

	public void setRigidBody(RigidBody rigidBody) {
		myRigidBody = rigidBody;
	}

	public RigidBody getRigidBody() {
		return myRigidBody;
	}

	public boolean isPositionConstrained() {
		return myPositionConstraint != null;
	}

	public boolean isTransparent() {
		return getRestitution() < 0;
	}

	@Override
	public void addForce(Vector force) {
		myNetInternalForce = myNetInternalForce.plus(force);
	}

	@Override
	public void removeForce(Vector force) {
		myNetInternalForce = myNetInternalForce.minus(force);
	}

	@Override
	public void addPointingForce(double magnitude) {
		myDirForceMagnitude += magnitude;
	}
	
	@Override
	public void removePointingForce(double magnitude) {
		myDirForceMagnitude -= magnitude;
	}

}
