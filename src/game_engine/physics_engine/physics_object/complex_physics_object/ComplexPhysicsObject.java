package game_engine.physics_engine.physics_object.complex_physics_object;

import game_engine.physics_engine.Vector;
import game_engine.physics_engine.complex.ComplexPhysicsEngine;
import game_engine.physics_engine.complex.Joint;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.RectangleBody;
import game_engine.physics_engine.complex.RigidBody;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_player.Animation;

import java.util.List;

public abstract class ComplexPhysicsObject extends IPhysicsObject{
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

	public ComplexPhysicsObject(ComplexPhysicsEngine physics, int widthPixels, int heightPixels, Material material, int xPosPixels, int yPosPixels, Animation a) {
		this(physics, new RectangleBody(heightPixels, widthPixels), material, xPosPixels, yPosPixels, a);
	}

	public ComplexPhysicsObject(ComplexPhysicsEngine physics, RigidBody rigidBody, Material material, int xPosPixels, int yPosPixels, Animation a) {
		myPhysics = physics;
		myRigidBody = rigidBody;
		myMaterial = material;

		myPosition = ComplexPhysicsEngine.vectorPixelsToMeters(xPosPixels, yPosPixels);
		myVelocity = new Vector();
		myNetInternalForce = new Vector();
		myDirForceMagnitude = 0;

		myInvMass = computeInvMass();
		myAccel = computeAccel();
		
		addObserver(a);
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
	
	public void move(Vector v) {
	    applyImpulse(v);
	}

}
