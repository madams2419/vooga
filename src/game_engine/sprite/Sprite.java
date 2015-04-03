package game_engine.sprite;

import java.util.Observable;
import game_engine.Animation;
import game_engine.HitBox;
import game_engine.PhysicsEngine;

/**
 * Abstract class for the creation of multiple sprite types
 * @author 
 *
 */
public abstract class Sprite extends Observable{
	String name;
	double id;
	double x;
	double y;
	double targetX;
	double targetY;
	double velocity;
	double acceleration;
	State spriteState;
	Animation animation;
	HitBox hitBox;
	PhysicsEngine physics;
	
	/**
	 * Blank Constructor
	 */
	public Sprite() {
		// TODO
	    this.id = 0;
	    animation = new Animation(this);
	}
	
	/**
	 * states for sprite
	 */
	private enum State {
	        IDLE,
	        WALK,
		JUMP,
		FLOAT,
		MOVE,
		BOUNCE
		// TODO add more states (or read these in from file)
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
	
	public void setVelocity(double vel){
		velocity = vel;
		setChanged();
		notifyObservers();
	}
	public double getVelocity(){
		return velocity;
	}
	
	public void setAcceleration(double accel){
		acceleration = accel;
		setChanged();
		notifyObservers();
	}
	public double getAcceleration(){
		return acceleration;
	}
	
	public void setTargetX(double x){
		targetX = x;
		setChanged();
		notifyObservers();
	}
	public void setTargetY(double y){
		targetY = y;
		setChanged();
		notifyObservers();
	}

	public double getTargetX(){
		return targetX;
	}
	public double getTargetY(){
		return targetY;
	}
	
	
	public void setState(State state){
		spriteState = state;
		setChanged();
		notifyObservers();
		
	}
	
	public State getState(){
		return spriteState;
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
	public void definePhysics(PhysicsEngine physics){
	    this.physics = physics;
	}
	
	/**
	 * method getPhysics
	 * @return the PhysicsEngine of the current sprite
	 */
	public PhysicsEngine getPhysics(){
	    return this.physics;
	}
	
	/**
	 * method defineHitBox()
	 * defines the HitBox for sprite collision
	 */
	public void defineHitBox(){
	    this.hitBox = hitBox;
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
