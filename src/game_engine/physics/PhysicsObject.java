package game_engine.physics;

import java.awt.geom.Point2D;
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
	private List<Vector> myAppliedForces;
	private List<Joint> myJoints;

	public PhysicsObject(double mass, double restitution, String state, Vector position, Vector velocity, Vector accel, Vector... appliedForces) {
		setMass(mass);
		setRestitution(restitution);
		setState(state);
		setPosition(position);
		setVelocity(velocity);
		setAccel(accel);
		myAppliedForces = Arrays.asList(appliedForces);
	}

	public PhysicsObject(double mass, double restitution, String state, int xPos, int yPos) {
		this(mass, restitution, state, new Vector(xPos, yPos), new Vector(), new Vector());
	}

	public PhysicsObject(double mass, double restitution, int xPos, int yPos) {
		this(mass, restitution, "default", xPos, yPos);
	}

	public void update() {

	}

	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVelocity.plus(impulse.multiply(myInvMass));
		setVelocity(newVelocity);
	}

	public Vector getPos() {
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
