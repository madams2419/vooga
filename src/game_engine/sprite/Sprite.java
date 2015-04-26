package game_engine.sprite;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
<<<<<<< HEAD
import game_engine.physics.Vector;
import game_engine.physics.objects.PhysicsObject;
=======
<<<<<<< HEAD
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.RigidBody.RBodyType;
=======
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.IPhysicsObject;
>>>>>>> 22264357c8c80e754b05e8fb37839b38026dd385
import game_player.Animation;
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.ImageView;

public class Sprite extends Observable implements IActor {
<<<<<<< HEAD
=======
	private int myId;
	private String myName;
	private String myState;
	private Animation myAnimation;
<<<<<<< HEAD
	protected PhysicsObject myPhysicsObject;
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();
//	private HitBox myHitBox;
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
	
	private String state;
	private Animation animation;
	private Map<String, IAction> actions;
	private PhysicsObject physicsObject;
	
	public Sprite(PhysicsObject po, Animation a, String initialState) {
		state = initialState;
		physicsObject = po;
		animation = a;
		actions = new HashMap<>();
		addObserver(animation);
		addObserver(physicsObject);
		setChanged();
		notifyObservers();
		
<<<<<<< HEAD
=======
	}
	
	/**
	 * Blank Constructor
	 */
	public Sprite(PhysicsObject physics) {
		// TODO
	    myPhysicsObject = physics;
	    myId = 0;
	    myAnimation = new Animation(this,myPhysicsObject);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name
	 * @param name the string to name the sprite
	 */
	public Sprite(PhysicsObject physics, String name){
	    myPhysicsObject = physics;
	    myId = 0; //TODO make call to SpriteManager to get unique ID or don't allow sprite to constructed without ID
	    myName = name;
	    myAnimation = new Animation(this,myPhysicsObject);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name and specified id
	 * @param name the string to name the sprite
	 * @param id the id of the specific sprite
	 */
	public Sprite(PhysicsObject physics, String name, int id){
	    myPhysicsObject = physics;
	    myName = name;
	    myId = id;
	    myAnimation = new Animation(this,myPhysicsObject);
	}
	
	/**
	 * method update
	 * Updates the sprite
	 */
	public abstract void update();
=======
	protected IPhysicsObject myPhysicsObject;
	private Map<String, IAction> myActionMap = new HashMap<>();
>>>>>>> 22264357c8c80e754b05e8fb37839b38026dd385
	
	public Sprite(String state, Animation animation, IPhysicsObject physicsObject) {
		myState = state;
		myAnimation = animation;
		myPhysicsObject = physicsObject;
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
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

<<<<<<< HEAD
=======
	public void setID(int id){
	    myId = id;
	}
	
	public double getID(){
	    return myId;
	}

	public void setName(String name){
	    this.myName = name;
	}
	
	public String getName(){
	    return this.myName;
	}
	
<<<<<<< HEAD
//	public HitBox getHitBox(){
//	    return myHitBox;
//	}
	
	public void setPhysicsObject(PhysicsObject physicsObject){
	    myPhysicsObject = physicsObject;
=======
	public void setStateName(String movementName){
		myState = movementName;
		notifyObservers(myState);
>>>>>>> 22264357c8c80e754b05e8fb37839b38026dd385
	}
	
	@IActionAnnotation(numParams = 2, description = "moving forward/backward")
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
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