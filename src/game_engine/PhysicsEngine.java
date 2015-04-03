package game_engine;
/**
 * Defines the physics of a game/sprite
 * @author 
 *
 */
public class PhysicsEngine {
	private double accelerationCoefficient;
	
	/**
	 * method setAcceleration
	 * sets the physics acceleration
	 * @param acceleration
	 */
	public void setAcceleration(double acceleration){
		accelerationCoefficient = acceleration;
	}
}
