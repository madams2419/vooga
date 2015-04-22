package game_engine.collisions;

import java.util.List;
import java.util.ArrayList;

public class CollisionEngine {
	List<Collision> collisionList;
	
	public CollisionEngine() {
	    this(new ArrayList<>());
	}
	
	public CollisionEngine(List<Collision> list){
		collisionList = list;
	}

	public void checkCollisions(){
		collisionList.forEach(Collision::update);
	}
	
	public void addCollision(Collision c){
		collisionList.add(c);
	}
}