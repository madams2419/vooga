package game_engine.sprite;

import java.awt.event.ItemListener;
import java.util.Observable;

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
	double stateID;
	String state;
	HitBox hitBox;
	PhysicsEngine physics;
	
	/**
	 * Blank Constructor
	 */
	public Sprite() {
		// TODO
	    this.id = 0;
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name
	 * @param name the string to name the sprite
	 */
	public Sprite(String name){
	    this.name = name;
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
	
	
	public void setState(String s){
		state = s;
		setChanged();
		notifyObservers();
	}
	
	public String getState(){
		return state;
	}
	
	/**
	 * method setID
	 * sets the ID of the sprite
	 * @param id the double id to set to the sprite
	 */
	public void setID(double id){
	    this.id = id;
	}
	
	/**
	 * method getID
	 * gets the ID of the sprite
	 * @return double Id of the sprite
	 */
	public double getID(){
	    return this.id;
	}
	
	/**
	 * method setName
	 * sets the Name of the current sprite
	 * @param name String to name the sprite
	 */
	public void setName(String name){
	    this.name = name;
	}
	
	/**
	 * method getName
	 * @return String name of current sprite
	 */
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
	
	/**
	 * method getHitBox()
	 * @return the HitBox for the current sprite
	 */
	public HitBox getHitBox(){
	    return this.hitBox;
	}
	
	/**
	 * method setX()
	 * set the X coordinate of the sprite
	 * @param x the x coordinate
	 */
	public void setX(double x){
	    this.x = x;
	}
	
	/**
	 * method getX()
	 * @return x the XCoordinate of the sprite
	 */
	public double getX(){
	    return x;
	}
	
	/**
         * method setY()
         * set the Y coordinate of the sprite
         * @param y the y coordinate
         */
	public void setY(double y){
	    this.y = y;
	}
	
	/**
         * method getY()
         * @return y the YCoordinate of the sprite
         */
	public double getY(){
	    return y;
	}
	
	
}
