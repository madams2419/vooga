package game_engine.physics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionManager {
	
	private Map<PhysicsObject, Map<PhysicsObject, PhysicsCollision>> myCollisions;
	private PhysicsCollisionFactory myCollisionFactory;
	
	public CollisionManager() {
		myCollisions = new HashMap<>();
		myCollisionFactory = new PhysicsCollisionFactory();
	}
	
	public void update(List<PhysicsObject> myObjects) {
		for(int i = 0; i < myObjects.size(); i++) {
			PhysicsObject poA = myObjects.get(i);

			for(int j = i + 1; j < myObjects.size(); j++) {
				PhysicsObject poB = myObjects.get(j);
				PhysicsCollision collision = myCollisionFactory.createCollision(poA, poB);
				mapCollision(poA, poB, collision);
				mapCollision(poB, poA, collision);
			}
		}
	}
	
	public boolean isCollided(PhysicsObject poA, PhysicsObject poB) {
		return getCollision(poA, poB).isCollided();
	}
	
	public PhysicsCollision getCollision(PhysicsObject poA, PhysicsObject poB) {
		return myCollisions.get(poA).get(poB);
	}
	
	private void mapCollision(PhysicsObject poA, PhysicsObject poB, PhysicsCollision collision) {
		if(!myCollisions.containsKey(poA)) {
			myCollisions.put(poA, new HashMap<PhysicsObject, PhysicsCollision>());
		}
		
		myCollisions.get(poA).put(poB, collision);
	}

}
