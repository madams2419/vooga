package game_engine.collisions;

import java.util.List;
import java.util.ArrayList;

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
}