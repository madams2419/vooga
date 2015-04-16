package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.collision.HitBox;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.RigidBody.RBodyType;
import game_player.Animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.ImageView;

/**
 * Abstract class for the creation of multiple sprite types
 * @TODO remove observer observable and clean
 * @author 
 *
 */
public abstract class Sprite extends Observable implements IActor{
	
	private int myId;
	private String myName;	
	private String myState;
	private Animation myAnimation;
	protected PhysicsObject myPhysicsObject;
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();
	private HitBox myHitBox;
	
	
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
	
	public IAction createBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	    Class<?> runClass = null;
	    IAction classInstance = null;
	    String className = "game_engine." + behavior;
	    runClass = Class.forName(className);
	    return classInstance = (IAction) runClass.newInstance();
	    
	}
	
	public void addBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	   // myBehaviorMap.put(behavior, createBehavior(behavior));
	}
	
	public void removeBehavior(String behavior){
	    myBehaviorMap.remove(behavior);
	}
	
	public void runBehavior(String behavior, String... params){
	    myBehaviorMap.get(behavior).perform(params);
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
		setChanged();
		notifyObservers();
	}
	
	private IAction setState = (params) -> { // stateChanging
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
	
	public HitBox getHitBox(){
	    return myHitBox;
	}
	
	public void setPhysicsObject(PhysicsObject physicsObject){
	    myPhysicsObject = physicsObject;
	}
	
	public PhysicsObject getPhysicsObject(){
	    return myPhysicsObject;
	}
	
	
	public void moveX(double x){
		myPhysicsObject.setXPixels(
				myPhysicsObject.getXPixels() + x);
		setChanged();
                notifyObservers();
	}
	
	public void moveY(double y){
		myPhysicsObject.setYPixels(
				myPhysicsObject.getYPixels() + y);
		setChanged();
                notifyObservers();
	}
	
	public IAction getAction(String name) {
	    if (name.equals("setState")){
	        return setState;
	    }
	    return (params) -> {};
	}
	
	
	
	
//	public static void main(String[] args){
//	    Sprite player = new Enemy();
//	    player.addImage("idle", "idle");
//	    player.addImage("walk", "walk");
//	    player.addImage("jump", "jump");
//	    player.addImage("float", "float");
//	    player.addImage("move", "move");
//	    player.addImage("bounce", "bounce");
//	    
//	    player.setState("idle");
//	    player.setState("jump");
//	    
//	}

}
