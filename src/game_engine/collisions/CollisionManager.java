package game_engine.collisions;

import java.util.List;
import java.util.ArrayList;

public class CollisionManager {
	List<Collision> collisionList;
	
	public CollisionManager() {
	    this(new ArrayList<>());
	}
	
	public CollisionManager(List<Collision> list){
		collisionList = list;
	}

	public void checkCollisions(){
		collisionList.forEach(Collision::update);
	}
	
	public void addCollision(Collision c){
		collisionList.add(c);
	}
}