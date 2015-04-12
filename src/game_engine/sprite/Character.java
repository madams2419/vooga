package game_engine.sprite;

import java.util.HashMap;
import java.util.ResourceBundle;

import game_engine.IAction;
import game_engine.physics.Vector;

/**
 * class that implements behaviors for movable sprites
 * @author emresonmez
 *
 */

public abstract class Character extends Sprite {
	private Vector myPosition;
//	private ResourceBundle myStateNames = ResourceBundle 
//			.getBundle("resources.engineutilities/movements");
	private HashMap<String,String> myStateNameMap = new HashMap<>(); // TODO implement this 	
	
	// TODO check design on feeding 2 constructors into themselves
	public Character(){
		super();
		myPosition = myPhysicsObject.getPositionPixels();
	}
	
	public Character(String name){
		super(name);
		myPosition = myPhysicsObject.getPositionPixels();
	}

	public Character(String name, int id){
		super(name,id);
		myPosition = myPhysicsObject.getPositionPixels();
	}
	
	/**
	 * behaviors
	 * @TODO add state changes (going to do this from utilities file)
	 */
	// params[0] is pixels to move forward
	private IAction moveForward = (params) -> { // movement
		myPosition.setX(myPosition.getX()+Double.parseDouble(params[0]));
		setStateName("forward");
	};
	
	public IAction getMoveForward(){
	    return this.moveForward;
	}

	// params[0] is upward scaling factor
	private IAction jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.applyImpulse(myVector);
		setStateName("jump");
	};
	
	public IAction getJump(){
	    return this.jump;
	}
	
	// params[0] is sideways scaling fire
	private IAction sprint = (params) -> { // movement
		Vector myVector = new Vector(1*Double.parseDouble(params[0]),0);
		myPhysicsObject.applyImpulse(myVector);
		setStateName("sprint");
	};
	
	public IAction getSprint(){
	    return this.sprint;
	}
	
	// TODO physics change here?
	private IAction slide = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.applyImpulse(myVector);
		setStateName("slide");
	};
	
	public IAction getSlide(){
	    return this.slide;
	}
	
	private IAction bounce = (params) -> { // movement 
		setStateName("bounce");
		
	};
	
	public IAction getBounce(){
	    return this.bounce;
	}
	
	public void setStateName(String movementName){
	//	setState(myStateNames.getString(movementName));
	}
	
	public IAction getAction(String name) {
	    switch (name) {
	        case "bounce":
	            return bounce;
	        case "moveForward":
	            return moveForward;
	        case "sprint":
	            return sprint;
	    }
	    return super.getAction(name);
	        
	}
}
