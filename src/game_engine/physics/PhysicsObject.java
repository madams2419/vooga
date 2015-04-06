package game_engine.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsObject {

	private double myMass;
	private double myInvMass;
	private double myRestitution;

	private String myState;
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAccel;
	private List<Vector> myInternalForces;
	private List<Vector> myExternalForces;
	private List<Joint> myJoints;
	private PhysicsEngine myPhysics;

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, String state, Vector position, Vector velocity) {
		setMass(mass);
		setRestitution(restitution);
		setState(state);
		setPosition(position);
		setVelocity(velocity);
		myInternalForces = new ArrayList<>();
		myExternalForces = physics.getGlobalForces();
		myAccel = computeAccel();
	}

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, String state, int xPos, int yPos) {
		this(physics, mass, restitution, state, new Vector(xPos, yPos), new Vector());
	}

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, int xPos, int yPos) {
		this(physics, mass, restitution, "default", xPos, yPos);
	}

	public void update() {
		double dt = myPhysics.getTimeStep();
		myAccel = computeAccel();
		myVelocity = myVelocity.plus(myAccel).times(dt);
		myPosition = myPosition.plus(myVelocity).times(dt);
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

	public void setMass(double mass) {
		myMass = mass;
		myInvMass = (mass == 0) ? 0 : 1/mass;
	}

	public double getMass() {
		return myMass;
	}

	public double getInvMass() {
		return myInvMass;
	}

	public void setRestitution(double restitution) {
		myRestitution = restitution;
	}

	public double getRestitution() {
		return myRestitution;
	}

	public void setState(String state) {
		myState = state;
	}

	public String getState(String state) {
		return myState;
	}

}
