package game_engine.physics;

import game_engine.physics.RigidBody.RBodyType;

import java.util.List;
import java.util.Observable;

// TODO
// - remove all Pixels methods (everything just needs to use the scaler)

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

	public PhysicsObject(PhysicsEngine physics, RBodyType rbType, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels) {
		this(physics, RigidBodyFactory.createRigidBody(heightPixels, widthPixels, rbType), material, xPosPixels, yPosPixels);
	}

	public PhysicsObject(PhysicsEngine physics, RigidBody rigidBody, Material material, int xPosPixels, int yPosPixels) {
		myPhysics = physics;
		myRigidBody = rigidBody;
		myMaterial = material;

		myPosition = myPhysics.getScaler().vectorPixelsToMeters(xPosPixels, yPosPixels);
		myVelocity = new Vector();
		myNetInternalForce = new Vector();
		myDirForceMagnitude = 0;

		myInvMass = computeInvMass();
		myAccel = computeAccel();
	}

	public void update(double dt) {
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel.times(dt));
		myPosition = myPosition.plus(myVelocity.times(dt));

		setChanged();
		notifyObservers();
	}

	private double computeInvMass() {
		double mass = computeMass();
		if(mass == 0) {
			return 0;
		} else {
			return 1/mass;
		}
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
		addVelocity(impulse.times(myInvMass));
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
		return myPhysics.getScaler().vectorMetersToPixels(getPositionMeters());
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
		return myPhysics.getScaler().metersToPixels(getXMeters());
	}

	public void setXPixels(double xPixels) {
		myPosition = myPosition.setX(myPhysics.getScaler().pixelsToMeters(xPixels));
	}

	public double getYPixels() {
		return myPhysics.getScaler().metersToPixels(getYMeters());
	}

	public void setYPixels(double yPixels) {
		myPosition = myPosition.setY(myPhysics.getScaler().pixelsToMeters(yPixels));
	}

	public double getRadiusPixels() {
		return myPhysics.getScaler().metersToPixels(myRigidBody.getRadius());
	}
	
	public double getWidthPixels() {
		return myPhysics.getScaler().metersToPixels(myRigidBody.getWidth());
	}
	
	public double getHeightPixels() {
		return myPhysics.getScaler().metersToPixels(myRigidBody.getHeight());
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

}
