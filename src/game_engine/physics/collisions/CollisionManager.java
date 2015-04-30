package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionManager {
	
	private Map<PhysicsObject, Map<PhysicsObject, Collision>> myCollisions;
	private CollisionFactory myCollisionFactory;
	
	public CollisionManager() {
		myCollisions = new HashMap<>();
		myCollisionFactory = new CollisionFactory();
	}
	
	public void checkAndResolveCollisions(List<PhysicsObject> myObjects) {
		for(int i = 0; i < myObjects.size(); i++) {
			PhysicsObject poA = myObjects.get(i);

			for(int j = i + 1; j < myObjects.size(); j++) {
				PhysicsObject poB = myObjects.get(j);
				Collision collision = myCollisionFactory.createCollision(poA, poB);
				if(collision.isCollided()) {
					mapCollision(poA, poB, collision);
					mapCollision(poB, poA, collision);
					collision.resolve();
				}
			}
		}
	}
	
	public boolean isCollided(PhysicsObject poA, PhysicsObject poB) {
		return getCollision(poA, poB).isCollided();
	}
	
	public Collision getCollision(PhysicsObject poA, PhysicsObject poB) {
		return myCollisions.get(poA).get(poB);
	}
	
	private void mapCollision(PhysicsObject poA, PhysicsObject poB, Collision collision) {
		if(!myCollisions.containsKey(poA)) {
			myCollisions.put(poA, new HashMap<PhysicsObject, Collision>());
		}
		
		myCollisions.get(poA).put(poB, collision);
	}

}
