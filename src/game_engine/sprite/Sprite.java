package game_engine.sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import game_engine.Animation;
import game_engine.Behavior;
import game_engine.HitBox;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;

/**
 * Abstract class for the creation of multiple sprite types
 * @author 
 *
 */
public abstract class Sprite extends Observable{
	private String name;
	private String myState;
	private Animation animation;
	private HitBox hitBox;
	private PhysicsObject myPhysicsObject;
	private Map<String, Behavior> behaviorMap = new HashMap<>();
	
	/**
	 * Blank Constructor
	 */
	public Sprite() {
		// TODO
	    this.id = 0;
	    animation = new Animation(this);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name
	 * @param name the string to name the sprite
	 */
	public Sprite(String name){
	    this.id = 0;
	    this.name = name;
	    animation = new Animation(this);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name and specified id
	 * @param name the string to name the sprite
	 * @param id the id of the specific sprite
	 */
	public Sprite(String name, double id){
	    this.name = name;
	    this.id = id;
	    animation = new Animation(this);
	}
	/**
	 * method update
	 * Updates the sprite
	 */
	public abstract void update();
	
	public Behavior createBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	    Class<?> runClass = null;
	    Behavior classInstance = null;
	    String className = "game_engine." + behavior;
	    runClass = Class.forName(className);
	    return classInstance = (Behavior) runClass.newInstance();
	    
	}
	
	public void addBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	    behaviorMap.put(behavior, createBehavior(behavior));
	}
	
	public void removeBehavior(String behavior){
	    behaviorMap.remove(behavior);
	}
	
	public void runBehavior(String behavior){
	    behaviorMap.get(behavior).execute();
	}
	
	public void addImage(String state,String ImagePath){
	    animation.setImage(state, ImagePath);
	}
	
	public void removeImage(String state){
	    animation.removeImage(state);
	}
	
	public void setState(String state){
		myState = state;
		setChanged();
		notifyObservers();
		
	}
	
	public String getState(){
		return myState;
	}
	

	public void setID(double id){
	    this.id = id;
	}
	public double getID(){
	    return this.id;
	}
	

	public void setName(String name){
	    this.name = name;
	}
	public String getName(){
	    return this.name;
	}
	
	/**
	 * method definePhysics
	 * @param physics the corresponding physics engine to set to the sprite
	 */
	public void definePhysics(PhysicsObject physicsObject){
	    myPhysicsObject = physicsObject;
	}
	
	/**
	 * method getPhysics
	 * @return the PhysicsEngine of the current sprite
	 */
	public PhysicsObject getPhysics(){
	    return myPhysicsObject;
	}
	
	/**
	 * method defineHitBox()
	 * defines the HitBox for sprite collision
	 */
	public void defineHitBox(HitBox hitBox){
	    myHitBox = hitBox;
	}
	public HitBox getHitBox(){
	    return this.hitBox;
	}
	

	public void setX(double x){
	    this.x = x;
	}
	public double getX(){
	    return x;
	}
	

	public void setY(double y){
	    this.y = y;
	}
	public double getY(){
	    return y;
	}
	
	
}
