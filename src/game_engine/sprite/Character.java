package game_engine.sprite;

import java.util.HashMap;
import java.util.ResourceBundle;

import game_engine.IBehavior;
import game_engine.physics.Vector;

/**
 * class that implements behaviors for movable sprites
 * @author emresonmez
 *
 */

public abstract class Character extends Sprite {
	private Vector myPosition = myPhysicsObject.getPosition();
	private ResourceBundle myStateNames = ResourceBundle 
			.getBundle("resources.engineutilities/movements");
	private HashMap<String,String> myStateNameMap = new HashMap<>(); // TODO implement this 	
	
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
		setStateName("forward");
	};

	// params[0] is upward scaling factor
	private IBehavior jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*params[0]);
		myPhysicsObject.applyImpulse(myVector);
		setStateName("jump");
	};
	
	// params[0] is sideways scaling fire
	private IBehavior sprint = (params) -> { // movement
		Vector myVector = new Vector(1*params[0],0);
		myPhysicsObject.applyImpulse(myVector);
		setStateName("sprint");
	};
	
	private IBehavior slide = (params) -> { // movement
		 setStateName("slide");
		
	};
	
	private IBehavior bounce = (params) -> { // movement
		// add change state
		setStateName("bounce");
		
	};
	
	private void setStateName(String movementName){
		setState(myStateNames.getString(movementName));
	}
		
}
