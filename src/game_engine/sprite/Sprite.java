package game_engine.sprite;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.PhysicsObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.ImageView;
/**
 * 
 * @authors Brian Lavalee, Kevin Chang, Emre Sonmez
 * Sprite class to hold information for all characters in game
 */
public class Sprite extends Observable implements Observer, IActor {
	
	private String state;
	private Sprite owner; // null if no owner
	private double worth;
	private Animation animation;
	private Map<String, IAction> actions;
	private PhysicsObject physicsObject;
	
	public Sprite(PhysicsObject po, Animation a, String initialState, Sprite spriteOwner, double initialWorth) {
		state = initialState;
		physicsObject = po;
		animation = a;
		actions = new HashMap<>();
		owner = spriteOwner;
		worth = initialWorth;
		setObservations();
		setChanged();
		notifyObservers();
		buildActionMap();
	}
	
	private void setObservations() {
		addObserver(animation);
		animation.addObserver(this);
		physicsObject.addObserver(animation);
	}
	
	private void buildActionMap(){ 
		actions.put("moveForward", moveForward);
		actions.put("jump", jump);
		actions.put("setState", setState);
	}
	
	public void update(long timeLapse) {
	    physicsObject.update(timeLapse);
	    animation.update(timeLapse);
	
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
	private IAction setState = (params) -> {
		String newState = params[0];
		state = newState;
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
			+ " otherwise increments worth of parent sprite")
	private IAction incrementScore = (params) -> {
		if(owner.equals(null)){
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

	@IActionAnnotation(numParams = 2, description = "moves sprite forward in an x, y vector direction")
	private IAction moveForward = (params) -> {
		physicsObject.addVelocity(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	@IActionAnnotation(numParams = 1, description = "sprite jumps up or down")
	private IAction jump = (params) -> {
		Vector myVector = new Vector(0, Double.parseDouble(params[0]));
		physicsObject.addVelocity(myVector);
	};
	
	/**
	 * IAction getAction
	 * @param name
	 * @return action mapped to the value name
	 */
	public IAction getAction(String name) {
		return actions.get(name);
	}

	public void update(Observable source, Object arg) {
		try {
			Animation animation = (Animation) source;
			physicsObject.setRigidBody(animation.getRigidBody());
		}
		catch (Exception e) {
			// do nothing
		}
	}
}