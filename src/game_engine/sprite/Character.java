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
<<<<<<< HEAD
	private IBehavior moveForward = (params) -> { // movement
		myPosition.setX(params[0]);
=======
	public IBehavior moveForward = (params) -> { // movement
		myPosition.setX(Double.parseDouble(params[0]));
>>>>>>> 5906d07736834aa2964e861ab11f8aadea64f60e
		setStateName("forward");
	};

	// params[0] is upward scaling factor
<<<<<<< HEAD
	private IBehavior jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*params[0]);
=======
	public IBehavior jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
>>>>>>> 5906d07736834aa2964e861ab11f8aadea64f60e
		myPhysicsObject.applyImpulse(myVector);
		setStateName("jump");
	};
	
	// params[0] is sideways scaling fire
<<<<<<< HEAD
	private IBehavior sprint = (params) -> { // movement
		Vector myVector = new Vector(1*params[0],0);
=======
	public IBehavior sprint = (params) -> { // movement
		Vector myVector = new Vector(1*Double.parseDouble(params[0]),0);
>>>>>>> 5906d07736834aa2964e861ab11f8aadea64f60e
		myPhysicsObject.applyImpulse(myVector);
		setStateName("sprint");
	};
	
<<<<<<< HEAD
	private IBehavior slide = (params) -> { // movement
		 setStateName("slide");
		
	};
	
	private IBehavior bounce = (params) -> { // movement
		// add change state
=======
	// TODO physics change here?
	public IBehavior slide = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.applyImpulse(myVector);
		setStateName("slide");
	};
	
	public IBehavior bounce = (params) -> { // movement 
>>>>>>> 5906d07736834aa2964e861ab11f8aadea64f60e
		setStateName("bounce");
		
	};
	
<<<<<<< HEAD
	private void setStateName(String movementName){
		setState(myStateNames.getString(movementName));
	}
		
=======
	public void setStateName(String movementName){
		setState(myStateNames.getString(movementName));
	}

>>>>>>> 5906d07736834aa2964e861ab11f8aadea64f60e
}
