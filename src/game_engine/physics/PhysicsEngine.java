package game_engine.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import game_engine.sprite.Sprite;

// TODO
// - convert to controller classs
// - refactor with special controller for retreiving values in pixel form
// - implement regions (regions are sprites)
// - optimize net force computation

public class PhysicsEngine {

	private double myDrag;
	private Map<String, Vector> myGlobalForces;
	private Map<String, Vector> myGlobalAccels;
	private Vector myNetGlobalAccel;
	private Vector myNetGlobalForce;
	private PhysicsCollisionFactory myCollisionFactory;
	private RigidBodyFactory myRigidBodyFactory;
	private Scaler myScaler;
	
	private List<PhysicsObject> myObjects;

	public PhysicsEngine(double drag, double scaleFactor) {
		myObjects = new ArrayList<>();
		myGlobalForces = new HashMap<>();
		myGlobalAccels = new HashMap<>();
		myNetGlobalForce = Vector.ZERO;
		myNetGlobalAccel = Vector.ZERO;
		myCollisionFactory = new PhysicsCollisionFactory();
		myRigidBodyFactory = new RigidBodyFactory();
		myScaler = new Scaler(scaleFactor);
		myDrag = drag;
	}

	public PhysicsEngine() {
		this(Constants.DRAG_COEF, Constants.SCALE_FACTOR);
	}
	
	public void update(double timeStep) {
		updateCollisions();
		updatePhysicsObjects(timeStep);
	}
	
	private void updatePhysicsObjects(double timeStep) {
		myObjects.forEach(po -> po.update(timeStep));
	}

	//TODO move to collision manager
	private void updateCollisions() {
		for(int i = 0; i < myObjects.size(); i++) {
			PhysicsObject poA = myObjects.get(i);

			for(int j = i + 1; j < myObjects.size(); j++) {
				PhysicsObject b = myObjects.get(j);
				myCollisionMan
			}
		}
	}
	
    public static void main(String[] args){
        for(int i=1; i<11; i++){
             System.out.println("Count is: " + i);
        }
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
	
	public Scaler getScaler() {
		return myScaler;
	}
	
	public PhysicsObject addPhysicsObject(Material material, Vector position) {
		PhysicsObject physicsObject = new PhysicsObject(this, material, position);
		myObjects.add(physicsObject);
		return physicsObject;
	}
	
	public void removePhysicsObject(PhysicsObject physicsObject) {
		myObjects.remove(physicsObject);
	}
	
	public RigidBody makeRigidBody(double widthPx, double heightPx, String type) {
		double width = myScaler.pixelsToMeters(widthPx);
		double height = myScaler.pixelsToMeters(heightPx);
		return myRigidBodyFactory.createRigidBody(width, height, type);
	}
	
	public boolean isCollided(PhysicsObject poA, PhysicsObject poB) {
		//TODO
		return false;
	}

}
