package game_engine.collision;

/**
 * 
 * @author Michael Lee
 *
 */
public interface ICollision {
	
	public void setResolver(ICollisionResolver resolver);
	
	public void setDetector(ICollisionDetector detector);
	
	public void execute();
	
}
