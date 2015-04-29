package game_engine.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhysicsEngine {

	private double myDrag;
	private Map<String, Vector> myGlobalForces;
	private Map<String, Vector> myGlobalAccels;
	private Vector myNetGlobalAccel;
	private Vector myNetGlobalForce;
	private CollisionManager myCollisionManager;
	private RigidBodyFactory myRigidBodyFactory;
	private Scaler myScaler;
	
	private List<PhysicsObject> myObjects;

	public PhysicsEngine(double drag, double scaleFactor) {
		myObjects = new ArrayList<>();
		myGlobalForces = new HashMap<>();
		myGlobalAccels = new HashMap<>();
		myNetGlobalForce = computeNetGlobalForce();
		myNetGlobalAccel = computeNetGlobalAccel();
		myCollisionManager = new CollisionManager();
		myRigidBodyFactory = new RigidBodyFactory();
		myScaler = new Scaler(scaleFactor);
		myDrag = drag;
	}

	public PhysicsEngine() {
		this(Constants.DRAG_COEF, Constants.SCALE_FACTOR);
	}
	
	public void update(double timeStep) {
		myCollisionManager.checkAndResolveCollisions(myObjects);
		myObjects.forEach(po -> po.update(timeStep));

	}

	public Vector getDragForce(PhysicsObject physObj) {
		double dragCoef = - myDrag * physObj.getRigidBody().getCxArea();
		return physObj.getVelocity().times(dragCoef);
	}
	
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
		myNetGlobalForce = myNetGlobalForce.plus(force);
	}

	public void setGlobalAccel(String name, Vector accel) {
		myGlobalAccels.put(name, accel);
		myNetGlobalAccel = myNetGlobalAccel.plus(accel);
	}

	public void removeGlobalForce(String name) {
		Vector removedForce = myGlobalForces.remove(name);
		myNetGlobalForce = myNetGlobalForce.minus(removedForce);
	}

	public void removeGlobalAccel(String name) {
		Vector removedAccel = myGlobalAccels.remove(name);
		myNetGlobalAccel = myNetGlobalAccel.minus(removedAccel);
	}
	
	public Scaler getScaler() {
		return myScaler;
	}
	
	public PhysicsObject addPhysicsObject(RigidBody rBody, Material material, Vector position) {
		PhysicsObject physicsObject = new PhysicsObject(this, rBody, material, position);
		myObjects.add(physicsObject);
		return physicsObject;
	}
	
	public void removePhysicsObject(PhysicsObject physicsObject) {
		myObjects.remove(physicsObject);
	}
	
	public RigidBody createRigidBody(double widthPx, double heightPx) {
		double width = myScaler.pixelsToMeters(widthPx);
		double height = myScaler.pixelsToMeters(heightPx);
		return myRigidBodyFactory.createRigidBody(width, height);
	}
	
	public boolean isCollided(PhysicsObject poA, PhysicsObject poB) {
		return myCollisionManager.isCollided(poA, poB);
	}

}
