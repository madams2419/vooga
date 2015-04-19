package game_engine.physics_engine.complex;

import game_engine.physics_engine.physics_object.IPhysicsObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * physics engine super classes
 * contains update, add, remove, and resolve collision method definitions
 *
 */
public abstract class PhysicsEngine {
	private List<IPhysicsObject> myPhysicsObjects;
	
	public PhysicsEngine(){
		myPhysicsObjects = new ArrayList<>();
	}
	
	public abstract void update();
	
	public void addPhysicsObject(IPhysicsObject physicsObject){
		myPhysicsObjects.add(physicsObject);
	}
	
	public void removePhysicsObject(IPhysicsObject physicsObject){
		myPhysicsObjects.remove(physicsObject);
	}
	
	public List<IPhysicsObject> getUnmodifiablePhysicsObjects(){
		return Collections.unmodifiableList(myPhysicsObjects);
	}
	
	protected List<IPhysicsObject> getPhysicsObjects(){
		return myPhysicsObjects;
	}
	
	public abstract void resolveCollision(IPhysicsObject physicsObject1, IPhysicsObject physicsObject2);
}
