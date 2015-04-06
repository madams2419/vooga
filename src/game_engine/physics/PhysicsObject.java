package game_engine.physics;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
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

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, String state, Vector position, Vector velocity, Vector accel) {
		setMass(mass);
		setRestitution(restitution);
		setState(state);
		setPosition(position);
		setVelocity(velocity);
		setAccel(accel);
		myInternalForces = new ArrayList<>();
		myExternalForces = physics.getGlobalForces();
	}

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, String state, int xPos, int yPos) {
		this(physics, mass, restitution, state, new Vector(xPos, yPos), new Vector(), new Vector());
	}

	public PhysicsObject(PhysicsEngine physics, double mass, double restitution, int xPos, int yPos) {
		this(physics, mass, restitution, "default", xPos, yPos);
	}

	public void update() {
		double dt = physics.getTimeStep();



		myVelocity = myVelocity.plus(netForce.multiply(myInvMass * dt));

	}

	public Vector computeNetForce() {
		Vector netForce = new Vector();

		// sum external forces
		for(Vector eForce : myInternalForces) {
			netForce = netForce.plus(iForce);
		}

		// sum internal forces
		for(Vector iForce : myInternalForces) {
			netForce = netForce.plus(iForce);
		}
	}


	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVelocity.plus(impulse.multiply(myInvMass));
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

	public void setAccel(Vector accel) {
		myAccel = accel;
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
