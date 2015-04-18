package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics_engine.complex.ComplexPhysicsEngine;
import game_engine.physics_engine.complex.Joint;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RigidBody;
import game_engine.physics_engine.complex.RigidBodyFactory;
import game_engine.physics_engine.complex.Vector;
import game_engine.physics_engine.complex.RigidBody.RBodyType;
import game_engine.physics_engine.physics_object.IPhysicsObject;

import java.util.List;

public abstract class ComplexPhysicsObject implements IPhysicsObject{
	//TODO remove protected, add getters
	protected double myInvMass;
	protected Material myMaterial;
	protected Vector myPosition;
	protected Vector myVelocity;
	protected Vector myAccel;
	protected Vector myNetInternalForce;
	protected double myDirForceMagnitude;
	protected List<Joint> myJoints;
	protected ComplexPhysicsEngine myPhysics;

	protected RigidBody myRigidBody;

	public ComplexPhysicsObject(ComplexPhysicsEngine physics, RBodyType rbType, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels) {
		this(physics, RigidBodyFactory.createRigidBody(heightPixels, widthPixels, rbType), material, xPosPixels, yPosPixels);
	}

	public ComplexPhysicsObject(ComplexPhysicsEngine physics, RigidBody rigidBody, Material material, int xPosPixels, int yPosPixels) {
		myPhysics = physics;
		myRigidBody = rigidBody;
		myMaterial = material;

		myPosition = ComplexPhysicsEngine.vectorPixelsToMeters(xPosPixels, yPosPixels);
		myVelocity = new Vector();
		myNetInternalForce = new Vector();
		myDirForceMagnitude = 0;

		myInvMass = computeInvMass();
		myAccel = computeAccel();
	}

	public abstract void update();

	private double computeInvMass() {
		double mass = myMaterial.getDensity() * myRigidBody.getVolume();
		return 1/mass;
	}

	protected Vector computeAccel() {
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
	
	public double getXPosition() {
		return myPosition.getX();
	}

	public void setXPosition(double xMeters) {
		myPosition = myPosition.setX(xMeters);
	}

	public double getYPosition() {
		return myPosition.getY();
	}

	public void setYPosition(double yMeters) {
		myPosition = myPosition.setY(yMeters);
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
