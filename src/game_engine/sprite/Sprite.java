package game_engine.sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.ImageView;
import game_engine.IBehavior;
import game_engine.collision.HitBox;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import game_player.Animation;

/**
 * Abstract class for the creation of multiple sprite types
 * @TODO remove observer observable and clean
 * @author 
 *
 */
public abstract class Sprite extends Observable{
	
	private int myId;
	private String myName;	
	private String myState;
	private Animation myAnimation;
	protected PhysicsObject myPhysicsObject = new PhysicsObject(null, null, null, 0, 0);//new PhysicsObject(null, null, null, myName, new Vector(0,0), null);
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();

	
	/**
	 * Blank Constructor
	 */
	public Sprite() {
		// TODO
	    myId = 0;
	    myAnimation = new Animation(this);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name
	 * @param name the string to name the sprite
	 */
	public Sprite(String name){
	    myId = 0; //TODO make call to SpriteManager to get unique ID or don't allow sprite to constructed without ID
	    myName = name;
	    myAnimation = new Animation(this);
	}
	
	/**
	 * Constructor Sprite
	 * Creates sprite object with a defined name and specified id
	 * @param name the string to name the sprite
	 * @param id the id of the specific sprite
	 */
	public Sprite(String name, int id){
	    myName = name;
	    myId = id;
	    myAnimation = new Animation(this);
	}
	
	/**
	 * method update
	 * Updates the sprite
	 */
	public abstract void update();
	
	public IBehavior createBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	    Class<?> runClass = null;
	    IBehavior classInstance = null;
	    String className = "game_engine." + behavior;
	    runClass = Class.forName(className);
	    return classInstance = (IBehavior) runClass.newInstance();
	    
	}
	
	public void addBehavior(String behavior) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	    myBehaviorMap.put(behavior, createBehavior(behavior));
	}
	
	public void removeBehavior(String behavior){
	    myBehaviorMap.remove(behavior);
	}
	
	public void runBehavior(String behavior, String... params){
	    myBehaviorMap.get(behavior).execute(params);
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
	    myAnimation.getImageView().resize(xSize, ySize);
	}
	
	public void setState(String state){
		myState = state;
		setChanged();
		notifyObservers();
	}
	
	private IBehavior setState = (params) -> { // stateChanging
            String state = params[0];
            setState(state);
	};
	
	public IBehavior setStateBehavior(){
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
	    return myAnimation.getHitBox();
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
