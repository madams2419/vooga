package game_engine.sprite;

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
	private Animation animation;
	private Map<String, IAction> actions;
	private PhysicsObject physicsObject;
	
	public Sprite(PhysicsObject po, Animation a, String initialState) {
		state = initialState;
		physicsObject = po;
		animation = a;
		addObserver(animation);
		addObserver(physicsObject);
		setChanged();
		notifyObservers();
		
		buildActionMap();
		actions = new HashMap<>();
	}
	
	private void buildActionMap(){ 
		actions.put("moveForward", moveForward);
		actions.put("jump", jump);
		actions.put("setState", setState);
	}
	
	public void update() {
	    physicsObject.update();
	    animation.update();
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

	private IAction moveForward = (params) -> {
		physicsObject.applyImpulse(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
	};

	private IAction jump = (params) -> {
		Vector myVector = new Vector(0, Double.parseDouble(params[0]));
		physicsObject.applyImpulse(myVector);
	};

	public IAction getAction(String name) {
		return actions.get(name);
	}
}