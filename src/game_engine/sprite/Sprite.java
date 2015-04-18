package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.behaviors.IAction;
import game_engine.collision.HitBox;
import game_engine.physics_engine.complex.Material;
import game_engine.physics_engine.complex.Vector;
import game_engine.physics_engine.complex.RigidBody.RBodyType;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_player.Animation;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.ImageView;

/**
 * new sprite class
 * TODO (emre) observer-observable on imageView in Animation to PhysicsObject
 * @author emresonmez
 *
 */
public class Sprite {
	private int myId;
	private String myName;	
	private String myState;
	private Animation myAnimation;
	protected IPhysicsObject myPhysicsObject;
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();
	private Map<String, IAction> myActionMap = new HashMap<>();
	private Map<Sprite, Integer> myCollectibleMap = new HashMap<>();
	private double xPosition;
	private double yPosition;
	
	public Sprite(String state, Animation animation, IPhysicsObject physicsObject) {
		myState = state;
		myAnimation = animation;
		myPhysicsObject = physicsObject;
		buildActionMap();
	}
	
	/**
	 * TODO refactor this into better design
	 */
	private void buildActionMap(){ 
		myActionMap.put("bounce", bounce);
		myActionMap.put("moveForward", moveForward);
		myActionMap.put("sprint", sprint);
		myActionMap.put("jump", jump);
	}
	
	/**
	 * updates Sprites (TODO remove after observer/observable implemented by Emre)
	 */
	public void update(){ // TODO eventually won't be necessary after observer/observable
		xPosition = myPhysicsObject.getXPosition();
		yPosition = myPhysicsObject.getYPosition();
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
	
	public void setStateName(String movementName){
		myState = movementName;
	}
	
	/**
	 * behaviors
	 * @TODO add state changes (going to do this from utilities file)
	 */
	// params[0] is pixels to move forward
	private IAction moveForward = (params) -> { // movement
//	        myPhysicsObject.applyImpulse(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
		setStateName("forward");
	};

	// params[0] is upward scaling factor
	private IAction jump = (params) -> { // movement
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
//		myPhysicsObject.applyImpulse(myVector);
		setStateName("jump");
	};
	
	// params[0] is sideways scaling fire
	private IAction sprint = (params) -> { // movement
//		Vector myVector = new Vector(1*Double.parseDouble(params[0]),0);
//		myPhysicsObject.applyImpulse(myVector);
		setStateName("sprint");
	};
	
	public IAction getSprint(){
	    return this.sprint;
	}
	
	// TODO physics change here?
	private IAction slide = (params) -> { // movement
//		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
//		myPhysicsObject.applyImpulse(myVector);
		setStateName("slide");
	};
	
	private IAction bounce = (params) -> { // movement 
		setStateName("bounce");
	};

	public IAction getAction(String name) {
		return myActionMap.get(name);
	}
	
	/**
	 * collectible sprites
	 */
	public void addCollectible(Sprite collectible){
	    myCollectibleMap.put(collectible, 0);
	}
	
	public void removeCollectible(Sprite collectible){
	    myCollectibleMap.remove(collectible);
	}
	
	public void incrementCount(Sprite collectible, int amount){
	   myCollectibleMap.put(collectible, myCollectibleMap.get(collectible)+amount); 
	}
	
	public void decrementCount(Sprite collectible, int amount){
	   myCollectibleMap.put(collectible, myCollectibleMap.get(collectible)-amount); 

	}
	
	public void setCount(Sprite collectible, int count){
	    myCollectibleMap.put(collectible, count);
	}
	
	public int getCount(Sprite collectible){
	    return myCollectibleMap.get(collectible);
	}
	
}
