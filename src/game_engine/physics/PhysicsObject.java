package game_engine.physics;

import java.util.List;
import java.util.Observable;

// TODO
// - ability to run back time...some mechanism to do that

public class PhysicsObject extends Observable {

	private double myInvMass;
	private Material myMaterial;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private Vector myNetInternalForce;
	private double myDirForceMagnitude;
	private List<Joint> myJoints;
	private PhysicsEngine myPhysics;

	private RigidBody myRigidBody;

	public PhysicsObject(PhysicsEngine physics, RigidBody shape, Material material, int xPosPixels, int yPosPixels) {
		myPhysics = physics;
		myRigidBody = shape;
		myMaterial = material;

		myPosition = PhysicsEngine.vectorPixelsToMeters(xPosPixels, yPosPixels);
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
		if(myPosition.getY() <= myPhysics.getGround() + myRigidBody.getRadius()) {
			myPosition = myPosition.setYCopy(myPhysics.getGround() + myRigidBody.getRadius());
			myVelocity = myVelocity.setYCopy(0);
		}

		setChanged();
		notifyObservers();
	}

	private double computeInvMass() {
		double mass = myMaterial.getDensity() * myRigidBody.getVolume();
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
		Vector intForce = direction.times(myDirForceMagnitude);
		return intForce.plus(myPhysics.getDragForce(this));
	}

	public void addForce(Vector force) {
		myNetInternalForce = myNetInternalForce.plus(force);
	}

	public void removeForce(Vector force) {
		myNetInternalForce = myNetInternalForce.minus(force);
	}

	public void addDirectionalForce(double magnitude) {
		myDirForceMagnitude += magnitude;
	}

	public void removeDirectionalForce(double magnitude) {
		myDirForceMagnitude -= magnitude;
	}

	public void applyImpulse(Vector impulse) {
		applyVelocity(impulse.times(myInvMass));
	}

	public void applyVelocity(Vector velocity) {
		Vector newVelocity = myVelocity.plus(velocity);
		setVelocity(newVelocity);
	}

	public Vector getPositionMeters() {
		return myPosition;
	}

	public Vector getPositionPixels() {
		return PhysicsEngine.vectorMetersToPixels(getPositionMeters());
	}

	public double getXMeters() {
		return myPosition.getX();
	}

	public void setXMeters(double xMeters) {
		myPosition = myPosition.setXCopy(xMeters);
	}

	public double getYMeters() {
		return myPosition.getY();
	}

	public void setYMeters(double yMeters) {
		myPosition = myPosition.setYCopy(yMeters);
	}

	public double getXPixels() {
		return PhysicsEngine.metersToPixels(getXMeters());
	}

	public void setXPixels(double xPixels) {
		myPosition = myPosition.setXCopy(PhysicsEngine.pixelsToMeters(xPixels));
	}

	public double getYPixels() {
		return PhysicsEngine.metersToPixels(getYMeters());
	}

	public void setYPixels(double yPixels) {
		myPosition = myPosition.setYCopy(PhysicsEngine.pixelsToMeters(yPixels));
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

	public double getRestitution() {
		return myMaterial.getRestitution();
	}

	public void setMaterial(Material material) {
		myMaterial = material;
		myInvMass = computeInvMass();
	}

	public RigidBody getRigidBody() {
		return myRigidBody;
	}

	public void setShape(RigidBody shape) {
		myRigidBody = shape;
		myInvMass = computeInvMass();
	}

}
