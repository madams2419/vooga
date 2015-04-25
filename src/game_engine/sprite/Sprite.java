package game_engine.sprite;

import game_engine.annotation.IActionAnnotation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
<<<<<<< HEAD
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.IPhysicsObject;
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
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
import game_player.Animation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.ImageView;

public class Sprite extends Observable implements IActor {
	private int myId;
	private String myName;
	private String myState;
	private Animation myAnimation;
	protected IPhysicsObject myPhysicsObject;
	private Map<String, IAction> myActionMap = new HashMap<>();
	protected PhysicsObject myPhysicsObject;
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();
//	private HitBox myHitBox;
	
	
	/**
	 * Testing constructor
	 */
	public Sprite(String defaultState, String defaultImage, int height, int width, RBodyType rbType,
			PhysicsEngine globalPhysics, Material material, int startX, int startY) {
		myId = 0;
		myPhysicsObject = new PhysicsObject(globalPhysics, rbType, height, width, material, startX, startY);
		myAnimation = new Animation(this, myPhysicsObject);
		addImage(defaultState, defaultImage);
		setState(defaultState);
		setImageSize(height, width);
		
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
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
	
	public Sprite(String state, Animation animation, IPhysicsObject physicsObject) {
		myState = state;
		myAnimation = animation;
		myPhysicsObject = physicsObject;
		buildActionMap();
		addObserver(animation);
	}
	
	private void buildActionMap(){ 
		myActionMap.put("moveForward", moveForward);
		myActionMap.put(jump.toString(), jump);
		myActionMap.put("setState", setState);
		
	}
	
	public void update() {
	    myPhysicsObject.update();
	}
	
	public IPhysicsObject getPhysicsObject() {
	    return myPhysicsObject;
	}
	
	public void addImage(String state,String ImagePath){
	    myAnimation.setImage(state, ImagePath);
	}
	
	public void removeImage(String state){
	    myAnimation.removeImage(state);
	}
	
	public ImageView getImageView(){
	    return myAnimation.getImageView();
	}
	
	public void setImageSize(double xSize, double ySize){
	    myAnimation.getImageView().setFitHeight(ySize);
	    myAnimation.getImageView().setFitWidth(xSize);
	}
	
	public void setState(String state){
		myState = state;
	}
	
	private IAction setState = (params) -> {
            String state = params[0];
            setState(state);
	};
	

	public IAction setStateBehavior(){

	    return setState;
	}
	
	public String getState(){
		return myState;
	}

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
	public void setStateName(String movementName){
		myState = movementName;
		notifyObservers(myState);
=======
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
>>>>>>> 76ac245ad5aa02e6c3f45238f1cc6763a4d3c008
	}
	
	@IActionAnnotation(numParams = 2, description = "moving forward/backward")
	private IAction moveForward = (params) -> {
	    myPhysicsObject.move(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
		setStateName("forward");
	};
	
	@IActionAnnotation(numParams = 1,description = "jumping movement")
	private IAction jump = (params) -> {
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.move(myVector);
		setStateName("jump");
	};
	
	public IAction getAction(String name) {
		return myActionMap.get(name);
	}
	
	public static void main(String[] args) {
		Field[] fields = Sprite.class.getDeclaredFields();
		for (Field field: fields) {
			if (field.isAnnotationPresent(IActionAnnotation.class)){
				System.out.println(field.getName());
			}
		}
	}
}