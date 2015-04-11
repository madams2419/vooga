package game_engine.collision;

import java.util.List;

/**
 * Class checks for collision occurrences between sprites
 * @author Michael Lee
 *
 */

public class CollisionEngine {
	List<Collision> collisionList;
	
	
	public CollisionEngine(List<Collision> list){
		collisionList = list;
	}
	
	/**
	 * 
	 * @param spriteA
	 * @param spriteB
	 * Checks for collision, executes behavior for collision
	 * PPC does not work if the imageview sets a fitwidth/height; resizing image within the imageview
	 *
	 */

	public void checkCollisions(){
		collisionList.forEach(Collision::getColliding);
	}
	
	public void addCollision(Collision c){
		collisionList.add(c);
	}
		
	public void removeCollision(Collision c){
		collisionList.remove(c);
	}
}
