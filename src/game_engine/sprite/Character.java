package game_engine.sprite;

import game_engine.behaviors.IAction;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import java.util.Arrays;
import java.util.HashMap;

/**
 * class that implements behaviors for movable sprites
 * @author emresonmez
 *
 */

public abstract class Character extends Sprite {
    private Vector myPosition;
//  private ResourceBundle myStateNames = ResourceBundle 
//                  .getBundle("resources.engineutilities/movements");
    private HashMap<String,String> myStateNameMap = new HashMap<>(); // TODO implement this         
    
    // TODO check design on feeding 2 constructors into themselves
    public Character(PhysicsObject physics){
            super(physics);
            myPosition = myPhysicsObject.getPositionPixels();
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
	private IAction moveForward = (params) -> { // movement
	        myPhysicsObject.applyImpulse(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
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
	
	//TODO: Replace with reflection or map?
	public IAction getAction(String name) {
	    System.out.println(name);
	    switch (name) {
	        case "bounce":
	            return bounce;
	        case "moveForward":
	            return moveForward;
	        case "sprint":
	            return sprint;
	        case "jump":
	            return jump;
	    }
	    return super.getAction(name);
	        
	}
}
