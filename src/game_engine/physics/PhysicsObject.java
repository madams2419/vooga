package game_engine.physics;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class PhysicsObject {

	private double myMass;
	private double myInvMass;
	private double myRestitution;

	private Point2D myPos;
	private Vector myVel;
	private Vector myAccel;
	private List<Vector> myAppliedForces;
	private List<Joint> myJoints;

	public PhysicsObject(double mass, double restitution, Point2D pos, Vector vel, Vector accel, Vector... appliedForces) {
		setPos(pos);
		setVel(vel);
		setAccel(accel);
		setMass(mass);
		setRestitution(restitution);
		myAppliedForces = Arrays.asList(appliedForces);
	}

	public PhysicsObject(double mass, double restitution, int xPos, int yPos) {
		this(mass, restitution, new Point2D.Double(xPos, yPos), new Vector(), new Vector());
	}

	public void update() {

	}

	public Point2D getPos() {
		return myPos;
	}

	public void setPos(Point2D pos) {
		myPos = pos;
	}

	public Vector getVel() {
		return myVel;
	}

	public void setVel(Vector vel) {
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
		myInvMass = 1/mass;
	}

	public double getMass() {
		return myMass;
	}

	public void setRestitution(double restitution) {
		myRestitution = restitution;
	}

}
