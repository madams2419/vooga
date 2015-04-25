package game_engine.collision;

import game_engine.sprite.Sprite;

/**
 * 
 * @author Michael Lee
 *
 */
public interface ICollisionDetector {
	
	//wondering if detector should contain references to sprites
	
	public boolean isColliding();

}
