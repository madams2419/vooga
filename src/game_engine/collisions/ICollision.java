package game_engine.collisions;

import game_engine.collisions.detectors.ICollisionDetector;

/**
 * 
 * @author Michael Lee
 *
 */
public interface ICollision {
	
	public void setDetector(ICollisionDetector detector);
	
	public void execute();
	
}
