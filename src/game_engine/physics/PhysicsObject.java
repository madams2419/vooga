package game_engine.physics;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class PhysicsObject {
	
	private Point2D myPos;
	private Vector myVel;
	private Vector myAccel;
	private List<Vector> myAppliedForces;
	
	public PhysicsObject(Point2D pos, Vector vel, Vector accel, Vector... appliedForces) {
		setPos(pos);
		setVel(vel);
		setAccel(accel);
		myAppliedForces = Arrays.asList(appliedForces);
	}
	
	public PhysicsObject(Point2D pos) {
		this(pos, new Vector(), new Vector());
	}
	
	public void update() {
		
	}

	public Point2D getPos() {
		return myPos;
	}

	public void setPos(Point2D myPos) {
		this.myPos = myPos;
	}

	public Vector getVel() {
		return myVel;
	}

	public void setVel(Vector myVel) {
		this.myVel = myVel;
	}

	public Vector getAccel() {
		return myAccel;
	}

	public void setAccel(Vector myAccel) {
		this.myAccel = myAccel;
	}

}
