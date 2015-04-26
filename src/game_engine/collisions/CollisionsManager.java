package game_engine.collisions;

import game_engine.sprite.Sprite;
import java.util.ArrayList;
import java.util.List;

public class CollisionsManager {
	List<Collision> collisionList;
	
	public CollisionsManager() {
	    this(new ArrayList<>());
	}
	
	public CollisionsManager(List<Collision> list){
		collisionList = list;
	}

	public void checkCollisions(){
		collisionList.forEach(Collision::update);
	}
	
	public void addCollision(Collision c){
		collisionList.add(c);
	}
	
	public void remove (Sprite sprite) {
	    collisionList.removeIf(collision -> collision.involves(sprite));
	}
}