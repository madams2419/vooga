package game_engine.physics_engine.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_engine.physics_engine.physics_object.complex_physics_object.ComplexPhysicsObject;
import game_engine.sprite.Sprite;

/**
 * complex physics engine implementation
 * TODO refactor with special controller for retrieving values in pixel form
 * TODO implement regions (regions are sprites)
 * TODO optimize net force computation
 */
public class ComplexPhysicsEngine extends PhysicsEngine{
	PhysicsCollisionFactory collisionFactory;
	//TODO move these to properties file
	private static double SCALE_FACTOR = 0.01; // pixel to meter scaling
	private static String GRAV_STRING = "gravity";
	private static double GRAV_MAGNITUDE = 9.8;
	private static double DRAG_COEF = 0.2;

	private double myGround;
	private double myTimeStep;
	private double myDrag;
	private Map<String, Vector> myGlobalForces;
	private Map<String, Vector> myGlobalAccels;
	private Vector myNetGlobalAccel;
	private Vector myNetGlobalForce;

	public ComplexPhysicsEngine(double groundPixels, double timeStep, double gravity, double drag) {
		super();
		myGround = pixelsToMeters(groundPixels);
		myTimeStep = timeStep;
		myGlobalForces = new HashMap<>();
		myGlobalAccels = new HashMap<>();
		myNetGlobalForce = new Vector();
		myNetGlobalAccel = new Vector();
		setGravity(gravity);
		myDrag = drag;
	}
	
	public ComplexPhysicsEngine(double groundPixels, double timeStep) {
		this(groundPixels, timeStep, GRAV_MAGNITUDE, DRAG_COEF);
	}
	
	public Vector getDragForce(ComplexPhysicsObject physObj) {
		double dragCoef = - myDrag * physObj.getRigidBody().getCxArea();
		return physObj.getVelocity().times(dragCoef);
	}

	public void setGravity(double gravity) {
		myGlobalAccels.put(GRAV_STRING, new Vector(0, -GRAV_MAGNITUDE));
		computeNetGlobalAccel();
	}
	
	@Override
	public void update() {
		getPhysicsObjects().forEach(physicsObject->physicsObject.update());
	}

	@Override
	public void resolveCollision(IPhysicsObject physicsObject1,
			IPhysicsObject physicsObject2) {
		// TODO resolve collisions
	}
	
	// TODO refactor below
	private Vector computeNetGlobalForce() {
		myNetGlobalForce = Vector.sum(getGlobalForces());
		return myNetGlobalForce;
	}

	private Vector computeNetGlobalAccel() {
		myNetGlobalAccel = Vector.sum(getGlobalAccels());
		return myNetGlobalAccel;
	}

	public Vector getNetGlobalForce() {
		return myNetGlobalForce;
	}

	public Vector getNetGlobalAccel() {
		return myNetGlobalAccel;
	}

	public List<Vector> getGlobalAccels() {
		return new ArrayList<>(myGlobalAccels.values());
	}

	public List<Vector> getGlobalForces() {
		return new ArrayList<>(myGlobalForces.values());
	}

	public void setGlobalForce(String name, Vector force) {
		myGlobalForces.put(name, force);
		computeNetGlobalForce();
	}

	public void setGlobalAccel(String name, Vector accel) {
		myGlobalAccels.put(name, accel);
		computeNetGlobalAccel();
	}

	public void removeGlobalForce(String name) {
		myGlobalForces.remove(name);
		computeNetGlobalForce();
	}

	public void removeGlobalAccel(String name) {
		myGlobalAccels.remove(name);
		computeNetGlobalAccel();
	}

	public double getTimeStep() {
		return myTimeStep;
	}

	public void setTimeStep(double timeStep) {
		myTimeStep = timeStep;
	}

	public double getGround() {
		return myGround;
	}

	/* Scaling utility functions */
	//TODO move these elsewhere

	public static double pixelsToMeters(double pixels) {
		return pixels * SCALE_FACTOR;
	}

	public static double metersToPixels(double meters) {
		return meters / SCALE_FACTOR;
	}

	public static Vector vectorPixelsToMeters(Vector vectorPixels) {
		return vectorPixelsToMeters(vectorPixels.getX(), vectorPixels.getY());
	}

	public static Vector vectorPixelsToMeters(double xPixels, double yPixels) {
		return new Vector(pixelsToMeters(xPixels), pixelsToMeters(yPixels));
	}

	public static Vector vectorMetersToPixels(Vector vector) {
		return vectorMetersToPixels(vector.getX(), vector.getY());
	}

	public static Vector vectorMetersToPixels(double xMeters, double yMeters) {
		return new Vector(metersToPixels(xMeters), metersToPixels(yMeters));
	}

}
