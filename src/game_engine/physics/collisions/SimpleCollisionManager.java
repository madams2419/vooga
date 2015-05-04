package game_engine.physics.collisions;

import game_engine.physics.PhysicsObject;
import game_engine.physics.SimplePhysicsObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCollisionManager {
	
	private Map<SimplePhysicsObject, Map<SimplePhysicsObject, SimpleCollision>> myCollisions;
	private SimpleCollisionFactory myCollisionFactory;
	
	public SimpleCollisionManager() {
		myCollisions = new HashMap<>();
		myCollisionFactory = new SimpleCollisionFactory();
	}
	
	public void checkAndResolveCollisions(List<SimplePhysicsObject> myObjects) {
		for(int i = 0; i < myObjects.size(); i++) {
			SimplePhysicsObject poA = myObjects.get(i);

			for(int j = i + 1; j < myObjects.size(); j++) {
				SimplePhysicsObject poB = myObjects.get(j);
				SimpleCollision collision = myCollisionFactory.createCollision(poA, poB);
				mapCollision(poA, poB, collision);
				mapCollision(poB, poA, collision);
				if(collision.isCollided()) {
					collision.resolve();
				}
			}
		}
	}
	
	public boolean isCollided(PhysicsObject poA, PhysicsObject poB) {
		SimpleCollision collision = getCollision(poA, poB);
		if(collision == null) {
			return false;
		}
		
		return collision.isCollided();
	}
	
	public SimpleCollision getCollision(PhysicsObject poA, PhysicsObject poB) {
		return myCollisions.get(poA).get(poB);
	}
	
	private void mapCollision(SimplePhysicsObject poA, SimplePhysicsObject poB, SimpleCollision collision) {
		if(!myCollisions.containsKey(poA)) {
			myCollisions.put(poA, new HashMap<SimplePhysicsObject, Collision>());
		}
		
		myCollisions.get(poA).put(poB, collision);
	}

}
