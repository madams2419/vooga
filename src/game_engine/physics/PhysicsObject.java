package game_engine.physics;

import java.util.List;
import java.util.Observable;
import java.util.function.Supplier;

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
	}
	
	public PhysicsObject(PhysicsEngine physics, Material material, Vector positionPx) {
		this(physics, RigidBody.POINT, material, positionPx);
	}

	public void update(double dt) {
		//TODO replace with strategy pattern
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
		//TODO replace with strategy pattern
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

	protected Vector getPositionMeters() {
		return myPosition;
	}

	public Vector getPositionPixels() {
		return myScaler.vectorMetersToPixels(getPositionMeters());
	}

	protected double getXMeters() {
		return myPosition.getX();
	}

	protected void setXMeters(double xMeters) {
		myPosition = myPosition.setX(xMeters);
	}

	protected double getYMeters() {
		return myPosition.getY();
	}

	protected void setYMeters(double yMeters) {
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
	
	public void setRigidBody(RigidBody rigidBody) {
		myRigidBody = rigidBody;
	}

	public RigidBody getRigidBody() {
		return myRigidBody;
	}

}
