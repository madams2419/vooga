package game_engine.physics;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class PhysicsObject {
	
	private Point2D myPos;
	private Vector myVel;
	private Vector myAccel;
	private double myRestitution;
	private List<Vector> myAppliedForces;
	private List<Joint> myJoints;
	
	public PhysicsObject(Point2D pos, Vector vel, Vector accel, double restitution, Vector... appliedForces) {
		setPos(pos);
		setVel(vel);
		setAccel(accel);
		setRestitution(restitution);
		myAppliedForces = Arrays.asList(appliedForces);
	}
	
	public PhysicsObject(Point2D pos) {
		this(pos, new Vector(), new Vector(), 0);
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
	
	public void setRestitution(double restitution) {
		myRestitution = restitution;
	}

}
