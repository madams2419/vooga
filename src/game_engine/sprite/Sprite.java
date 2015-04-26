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
		addObserver(physicsObject);
		setChanged();
		notifyObservers();
		buildActionMap();
	}
	
	private void buildActionMap(){ 
		actions.put("moveForward", moveForward);
		actions.put("jump", jump);
		actions.put("setState", setState);
	}
	
	public void update(double frameRate) {
	    physicsObject.update(frameRate);
	    animation.update(frameRate);
	}
	
	public ImageView getImageView() {
	    return animation.getImageView();
	}
	
	public PhysicsObject getPhysicsObject() {
	    return physicsObject;
	}

	private IAction setState = (params) -> {
		String newState = params[0];
		state = newState;
		setChanged();
		notifyObservers();
	};

	public String getState() {
		return state;
	}
	
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

	public IAction getAction(String name) {
		return actions.get(name);
	}
}