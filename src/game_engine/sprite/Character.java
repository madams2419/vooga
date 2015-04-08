package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.physics.Vector;

/**
 * class that implements behaviors for movable sprites
 * @author emresonmez
 *
 */

public abstract class Character extends Sprite {
	private Vector myPosition = myPhysicsObject.getPosition();
	
	// TODO check design on feeding 2 constructors into themselves
	public Character(){
		super();
	}
	
	public Character(String name){
		super(name);
	}

	public Character(String name, int id){
		super(name,id);
	}
	
	/**
	 * behaviors
	 * @TODO add state changes (going to do this from utilities file)
	 */
	// params[0] is pixels to move forward
	private IBehavior moveForward = (params) -> { // movement
		myPosition.setX(params[0]);
	};

	// params[0] is upward scaling factor
	private IBehavior jump = (params) -> { // movement
		
		Vector myVector = new Vector(0,1*params[0]);
		myPhysicsObject.applyImpulse(myVector);
	};
	
	// params[0] is sideways scaling fire
	private IBehavior sprint = (params) -> { // movement
		Vector myVector = new Vector(1*params[0],0);
		myPhysicsObject.applyImpulse(myVector);
	};
	
	private IBehavior slide = (params) -> { // movement
		// add change state
		
	};
	
	private IBehavior bounce = (params) -> { // movement
		// add change state
		
	};
		
}
