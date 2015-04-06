package game_engine.physics;

import game_engine.HitBox;

import java.util.ArrayList;
import java.util.List;

public class PhysicsObject {

	private double myInvMass;
	private Material myMaterial;
	private HitBox myHitBox;
	private String myState;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private List<Vector> myInternalForces;
	private List<Vector> myExternalForces;
	private List<Joint> myJoints;
	private PhysicsEngine myPhysics;

	public PhysicsObject(PhysicsEngine physics, Material material, HitBox hitBox, String state, Vector position, Vector velocity) {
		setMaterial(material);
		setHitBox(hitBox);
		setState(state);
		setPosition(position);
		setVelocity(velocity);
		myInternalForces = new ArrayList<>();
		myExternalForces = physics.getGlobalForces();
		myInvMass = computeInvMass();
		myAccel = computeAccel();
	}

	public PhysicsObject(PhysicsEngine physics, Material material, HitBox hitBox, String state, int xPos, int yPos) {
		this(physics, material, hitBox, state, new Vector(xPos, yPos), new Vector());
	}

	public void update() {
		double dt = myPhysics.getTimeStep();
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel).times(dt);
		myPosition = myPosition.plus(myVelocity).times(dt);
	}

	public double computeInvMass() {
		double mass = myMaterial.getDensity() * myHitBox.getVolume();
		return 1/mass;
	}

	public Vector computeAccel() {
		Vector netForce = computeNetForce();
		return netForce.times(myInvMass);
	}

	public Vector computeNetForce() {
		int numForces = myExternalForces.size() + myInternalForces.size();

		// sum external forces
		Vector extSum = Vector.sum(myExternalForces);

		// sum internal forces
		Vector intSum = Vector.sum(myInternalForces);

		return extSum.plus(intSum).times(1/numForces);
	}

	public void addForce(Vector force) {
		myInternalForces.add(force);
	}

	public void removeForce(Vector force) {
		myInternalForces.remove(force);
	}

	public void addOppositionalForce(double magnitude) {
		//TODO
	}

	public void removeOppositionalForce(double magnitude) {
		//TODO
	}

	public void applyOppositionalForce(double magnitude) {
		//TODO
	}

	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVelocity.plus(impulse.times(myInvMass));
		setVelocity(newVelocity);
	}

	public Vector getPosition() {
		return myPosition;
	}

	public void setPosition(Vector position) {
		myPosition = position;
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

	public void setHitBox(HitBox hitBox) {
		myHitBox = hitBox;
		myInvMass = computeInvMass();
	}

	public void setState(String state) {
		myState = state;
	}

	public String getState(String state) {
		return myState;
	}

}
