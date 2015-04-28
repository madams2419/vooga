package game_engine.sprite;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.objects.PhysicsObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.ImageView;
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
	private PhysicsObject physicsObject;
	
	public Sprite(PhysicsObject po, Animation a, String initialState, 
			Sprite spriteOwner, double initialWorth) {
		state = initialState;
		physicsObject = po;
		animation = a;
		actions = new HashMap<>();
		owner = spriteOwner;
		worth = initialWorth;
		addObserver(animation);
		addObserver(physicsObject);
		setChanged();
		notifyObservers();
		buildActionMap();
	}
	
	public Sprite(PhysicsObject po, Animation a, String initialState, 
			double initialWorth) {
		state = initialState;
		physicsObject = po;
		animation = a;
		actions = new HashMap<>();
		owner = null;
		worth = initialWorth;
		addObserver(animation);
//		addObserver(physicsObject);
		setChanged();
		notifyObservers();
		buildActionMap();
	}
	
	/**
	 * method buildActionMap
	 * sets Strings to IAction objects
	 */
	private void buildActionMap(){ 
		actions.put("moveForward", moveForward);
		actions.put("jump", jump);
		actions.put("setState", setState);
	}
	
	public Animation getAnimation(){
	    return animation;
	}
	
	/**
	 * method update
	 * @param frameRate the frameRate with which to update items
	 * updates physicsObject and animation parameters at the specified frame rate
	 */
	public void update(double frameRate) {
	    physicsObject.update(frameRate);
	    animation.update(frameRate);
	    physicsObject.setPosition(animation.getPosition());
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
		physicsObject.applyImpulse(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	@IActionAnnotation(numParams = 1, description = "sprite jumps up or down")
	private IAction jump = (params) -> {
		Vector myVector = new Vector(0, Double.parseDouble(params[0]));
		physicsObject.applyImpulse(myVector);
	};
	
	/**
	 * IAction getAction
	 * @param name
	 * @return action mapped to the value name
	 */
	public IAction getAction(String name) {
		return actions.get(name);
	}
}