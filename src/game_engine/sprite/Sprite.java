package game_engine.sprite;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics.Vector;
import game_engine.physics.objects.SimplePhysicsObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.ImageView;
/**
 * 
 * @authors Brian Lavalee, Kevin Chang, Emre Sonmez
 * Sprite class to hold information for all characters in game
 */
public class Sprite extends Observable implements IActor {
	
	private String state;
	private Sprite owner; // null if no owner
	private double worth;
	private Animation animation;
	private Map<String, IAction> actions;
	private String id;
	private SimplePhysicsObject physicsObject;
	
	public Sprite(SimplePhysicsObject po, Animation a, String initialState, Sprite spriteOwner, double initialWorth, String id) {
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
		actions = buildActionMap();
		this.id = id;
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
	public SimplePhysicsObject getPhysicsObject() {
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

	@IActionAnnotation(numParams = 2, description = "moves sprite forward in an x, y vector direction", paramDetails = "two doubles")
	private IAction moveForward = (params) -> {
		physicsObject.applyControlImpulse(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};
	
	@IActionAnnotation(numParams = 1, description = "sprite jumps up or down", paramDetails = "double")
	private IAction jump = (params) -> {
		Vector myVector = new Vector(0, Double.parseDouble(params[0]));
		physicsObject.applyControlImpulse(myVector);
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
}