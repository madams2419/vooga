// This entire file is part of my masterpiece.
// Tony Qiao
package game_engine.collisions;

import java.util.ArrayList;
import java.util.List;

public class CollisionsManager<T> {
	List<Collision<T>> collisionList;
	
	public CollisionsManager() {
	    this(new ArrayList<>());
	}
	
	public CollisionsManager(List<Collision<T>> list){
		collisionList = list;
	}

	public void checkCollisions(){
		collisionList.forEach(Collision::update);
	}
	
	public void addCollision(Collision<T> c){
		collisionList.add(c);
	}
	
	public void remove (T sprite) {
	    collisionList.removeIf(collision -> collision.involves(sprite));
	}
}