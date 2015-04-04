package game_engine.sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import game_engine.Animation;
import game_engine.Behavior;
import game_engine.HitBox;
import game_engine.PhysicsEngine;
import game_engine.sprite.attributes.IMovement;

/**
 * Abstract class for the creation of multiple sprite types
 * @author 
 *
 */
public abstract class Sprite extends Observable implements IMovement{
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
	Map<String, Behavior> behaviorMap = new HashMap<>();
	
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
	
	public void addImage(Enum state,String ImagePath){
	    animation.setImage(state, ImagePath);
	}
	
	public void removeImage(Enum state){
	    animation.removeImage(state);
	}
	
	/**
	 * @see game_engine.sprite.IMovement#setVelocity(double)
	 */
	@Override
    public void setVelocity(double vel){
		velocity = vel;
		setChanged();
		notifyObservers();
	}
	/**
         * @see game_engine.sprite.IMovement#getVelocity()
         */
	@Override
    public double getVelocity(){
		return velocity;
	}
	
	/** 
	 * @see game_engine.sprite.IMovement#setAcceleration(double)
         */
	@Override
    public void setAcceleration(double accel){
		acceleration = accel;
		setChanged();
		notifyObservers();
	}
	/**
         * @see game_engine.sprite.IMovement#getAcceleration()
         */
	@Override
    public double getAcceleration(){
		return acceleration;
	}
	
	/** 
	 * @see game_engine.sprite.IMovement#setTargetX(double)
	 */
	@Override
    public void setTargetX(double x){
		targetX = x;
		setChanged();
		notifyObservers();
	}
	/**
	 * @see game_engine.sprite.IMovement#setTargetY(double)
	 */
	@Override
    public void setTargetY(double y){
		targetY = y;
		setChanged();
		notifyObservers();
	}

	/** 
	 * @see game_engine.sprite.IMovement#getTargetX()
         */
	@Override
    public double getTargetX(){
		return targetX;
	}
	/**
         * @see game_engine.sprite.IMovement#getTargetY()
         */
	@Override
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
	
	/* (non-Javadoc)
     * @see game_engine.sprite.IMovement#definePhysics(game_engine.PhysicsEngine)
     */
	@Override
    public void definePhysics(PhysicsEngine physics){
	    this.physics = physics;
	}
	
	/* (non-Javadoc)
     * @see game_engine.sprite.IMovement#getPhysics()
     */
	@Override
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
	

	/**
	 * @see game_engine.sprite.IMovement#setX(double)
         */
	@Override
    public void setX(double x){
	    this.x = x;
	}
	/**
         * @see game_engine.sprite.IMovement#getX()
         */
	@Override
    public double getX(){
	    return x;
	}
	

	/**
         * @see game_engine.sprite.IMovement#setY(double)
         */
	@Override
    public void setY(double y){
	    this.y = y;
	}
	/**
	 * @see game_engine.sprite.IMovement#getY()
	 */
	@Override
    public double getY(){
	    return y;
	}
	
	public static void main(String[] args){
	    Sprite player = new Enemy();
	    player.addImage(State.IDLE, "idle");
	    player.addImage(State.WALK, "walk");
	    player.addImage(State.JUMP, "jump");
	    player.addImage(State.FLOAT, "float");
	    player.addImage(State.MOVE, "move");
	    player.addImage(State.BOUNCE, "bounce");
	    
	    player.setState(State.IDLE);
	    player.setState(State.JUMP);
	    
	}
	
}
