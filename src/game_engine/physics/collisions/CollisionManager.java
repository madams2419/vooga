package game_engine.physics.collisions;

import game_engine.physics.IPhysicsObject;
import game_engine.physics.PhysicsObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionManager {
	
	private Map<IPhysicsObject, Map<IPhysicsObject, Collision>> myCollisions;
	private CollisionFactory myCollisionFactory;
	
	public CollisionManager() {
		myCollisions = new HashMap<>();
		myCollisionFactory = new CollisionFactory();
	}
	
	public void checkAndResolveCollisions(List<IPhysicsObject> myObjects) {
		for(int i = 0; i < myObjects.size(); i++) {
			IPhysicsObject poA = myObjects.get(i);

			for(int j = i + 1; j < myObjects.size(); j++) {
				IPhysicsObject poB = myObjects.get(j);
				Collision collision = myCollisionFactory.createCollision((PhysicsObject)poA, (PhysicsObject)poB);
				mapCollision(poA, poB, collision);
				mapCollision(poB, poA, collision);
				if(collision.isCollided()) {
					collision.resolve();
				}
			}
		}
	}
	
	public boolean isCollided(IPhysicsObject poA, IPhysicsObject poB) {
		Collision collision = getCollision(poA, poB);
		if(collision == null) {
			return false;
		}
		
		return collision.isCollided();
	}
	
	public Collision getCollision(IPhysicsObject poA, IPhysicsObject poB) {
		return myCollisions.get(poA).get(poB);
	}
	
	private void mapCollision(IPhysicsObject poA, IPhysicsObject poB, Collision collision) {
		if(!myCollisions.containsKey(poA)) {
			myCollisions.put(poA, new HashMap<IPhysicsObject, Collision>());
		}
		
		myCollisions.get(poA).put(poB, collision);
	}

}
