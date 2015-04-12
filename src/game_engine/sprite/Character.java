package game_engine.sprite;

import java.util.HashMap;
import java.util.ResourceBundle;
import game_engine.IBehavior;
import game_engine.physics.PhysicsObject;
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
	public Character(PhysicsObject physics){
		super(physics);
	}
	
	public Character(PhysicsObject physics, String name){
		super(physics, name);
		myPosition = myPhysicsObject.getPositionPixels();
	}

	public Character(PhysicsObject physics, String name, int id){
		super(physics, name,id);
		myPosition = myPhysicsObject.getPositionPixels();
	}
	
	/**
	 * behaviors
	 * @TODO add state changes (going to do this from utilities file)
	 */
	// params[0] is pixels to move forward
	private IBehavior moveForward = (params) -> { // movement
	        this.myPhysicsObject.applyImpulse(new Vector(Double.parseDouble(params[0]),Double.parseDouble(params[1])));
		setStateName("forward");
		System.out.println("moveForward");
	};
	
	public IBehavior getMoveForward(){
	    return this.moveForward;
	}

	// params[0] is upward scaling factor
	private IBehavior jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.applyImpulse(myVector);
		setStateName("jump");
		System.out.println("jump");
	};
	
	public IBehavior getJump(){
	    return this.jump;
	}
	
	// params[0] is sideways scaling fire
	private IBehavior sprint = (params) -> { // movement
		Vector myVector = new Vector(1*Double.parseDouble(params[0]),0);
		myPhysicsObject.applyImpulse(myVector);
		setStateName("sprint");
		System.out.println("sprint");
	};
	
	public IBehavior getSprint(){
	    return this.sprint;
	}
	
	// TODO physics change here?
	private IBehavior slide = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.applyImpulse(myVector);
		setStateName("slide");
		System.out.println("slide");
	};
	
	public IBehavior getSlide(){
	    return this.slide;
	}
	
	private IBehavior bounce = (params) -> { // movement 
		setStateName("bounce");
		System.out.println("bounce");
	};
	
	public IBehavior getBounce(){
	    return this.bounce;
	}
	
	public void setStateName(String movementName){
	//	setState(myStateNames.getString(movementName));
	}
}
