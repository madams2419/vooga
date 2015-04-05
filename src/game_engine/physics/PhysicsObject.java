package game_engine.physics;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class PhysicsObject {

	private double myMass;
	private double myInvMass;
	private double myRestitution;

	private String myState;
	private Point2D myPos;
	private Vector myVel;
	private Vector myAccel;
	private List<Vector> myAppliedForces;
	private List<Joint> myJoints;

	public PhysicsObject(double mass, double restitution, String state, Point2D pos, Vector vel, Vector accel, Vector... appliedForces) {
		setMass(mass);
		setRestitution(restitution);
		setState(state);
		setPos(pos);
		setVelocity(vel);
		setAccel(accel);
		myAppliedForces = Arrays.asList(appliedForces);
	}

	public PhysicsObject(double mass, double restitution, String state, int xPos, int yPos) {
		this(mass, restitution, state, new Point2D.Double(xPos, yPos), new Vector(), new Vector());
	}

	public PhysicsObject(double mass, double restitution, int xPos, int yPos) {
		this(mass, restitution, "default", xPos, yPos);
	}

	public void update() {

	}

	public void applyImpulse(Vector impulse) {
		Vector newVelocity = myVel.plus(impulse.multiply(myInvMass));
		setVelocity(newVelocity);
	}

	public Point2D getPos() {
		return myPos;
	}

	public void setPos(Point2D pos) {
		myPos = pos;
	}

	public Vector getVelocity() {
		return myVel;
	}

	public void setVelocity(Vector vel) {
		myVel = vel;
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
