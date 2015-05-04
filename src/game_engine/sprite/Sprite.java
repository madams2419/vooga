// This entire file is part of my masterpiece.
// Kevin Chang
package game_engine.sprite;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.PhysicsObject;
import game_engine.physics.utilities.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle; 
/**
 * 
 * @authors Brian Lavallee, Kevin Chang, Emre Sonmez
 * Sprite class to hold information for all characters in game
 */
public class Sprite extends Observable implements IActor {
	
	private String state;
	private Sprite owner; // null if no owner
	private double worth;
	private Animation animation;
	private Map<String, IAction> actions;
	private String id;
	private PhysicsObject physicsObject;
	private boolean alive;
	private TransitionObject transition;
	
	public Sprite(PhysicsObject po, Animation a, String initialState, Sprite spriteOwner, double initialWorth, String id) {
		state = initialState;
		physicsObject = po;
		animation = a;
		a.setState(initialState);
		actions = new HashMap<>();
		owner = spriteOwner;
		worth = initialWorth;
		this.id = id;
		alive = true;
		actions = buildActionMap();
		this.addObserver(animation);
		transition = new TransitionObject();
	}
	
	public void update(long timeLapse) {
		if(!physicsObject.isPositionConstrained()) {
			animation.updatePosition(physicsObject.getPositionPixels());
		}
	    animation.updateImage(timeLapse);
	    physicsObject.setRigidBody(animation.getRigidBody());
	}
	
	public Animation getAnimation(){
	    return animation;
	}
	
	/**
	 * method getImageView
	 * @return the imageview associated with the sprite
	 */
	public ImageView getImageView() {
	    return animation.getImageView();
	}
	
	public Rectangle getRect() {
		return animation.getRect();
	}
	
	/**
	 * getPhysicsObject() 
	 * @return the physics object associated with the sprite
	 */
	public PhysicsObject getPhysicsObject() {
	    return physicsObject;
	}
	
	/**
	 * IAction setState
	 * changes the state of the current sprite object
	 */
	@IActionAnnotation(description = "Changes the sprite state", numParams = 1, paramDetails = "String")
	private IAction setState = (params) -> {
		String newState = params[0];
		state = newState;
		animation.setState(newState);
		setChanged();
                notifyObservers();
	};
	
	/**
	 * method getState
	 * @return the current state associated with the sprite
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * method getWorth()
	 * @return the amount (points) assiociated with the sprite
	 */
	public Double getWorth(){
		return worth;
	}
	
	@IActionAnnotation(numParams = 1, description = "increments worth of sprite if no parent,"
			+ " otherwise increments worth of parent sprite", paramDetails = "double")
	private IAction incrementScore = (params) -> {
		if(owner==null){
			incrementScore(Double.parseDouble(params[0]));
		}else{
			owner.incrementScore(Double.parseDouble(params[0]));
		}
	};
	
	/**
	 * method incrementScore
	 * @param value
	 * adds amount value to your current score count
	 */
	private void incrementScore(double value){
		worth += value;
	}

	@IActionAnnotation(numParams = 2, description = "moves sprite forward in an x, y vector direction", paramDetails = "two doubles")
	private IAction moveForward = (params) -> {
		physicsObject.addVelocity(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	@IActionAnnotation(numParams = 1, description = "sprite jumps up or down", paramDetails = "double")
	private IAction jump = (params) -> {
	    System.out.println("jumps");
		Vector myVector = new Vector(0, Double.parseDouble(params[0]));
		physicsObject.addVelocity(myVector);
	};
	
	@IActionAnnotation (numParams = 0, description = "sprite is removed", paramDetails = "none")
	private IAction die = (params) -> {
	    alive = false;
	};
	
	@IActionAnnotation (numParams = 2, description = "add force", paramDetails = "2d vector")
	private IAction addForce = (params) -> {
	    physicsObject.addForce(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	
	@IActionAnnotation(numParams = 2, description = "sets sprites velocity", paramDetails = "double")
	private IAction setVelocity = (params) -> {
		physicsObject.setVelocity(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	@IActionAnnotation(numParams = 2, description = "remove velocity", paramDetails = "double")
	private IAction removeVelocity = (params) -> {
		physicsObject.removeVelocity(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	/**
	 * IAction getAction
	 * @param name
	 * @return action mapped to the value name
	 */
	public IAction getAction(String name) {
		return actions.get(name);
	}
	
	public boolean checkID (String string) {
	    return id.equals(string);
	}
	
	public void removeObserver(Observer obs){
	    this.removeObserver(obs);
	}
	
	public TransitionObject getTransitionObject(){
	    return this.transition;
	}
 
}