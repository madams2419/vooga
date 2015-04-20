package game_engine.sprite;

import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.IPhysicsObject;
import game_player.Animation;

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
	
	public Sprite(String state, Animation animation, IPhysicsObject physicsObject) {
		myState = state;
		myAnimation = animation;
		myPhysicsObject = physicsObject;
		buildActionMap();
		addObserver(animation);
	}
	
	private void buildActionMap(){ 
		myActionMap.put("moveForward", moveForward);
		myActionMap.put("jump", jump);
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
	
	public void setStateName(String movementName){
		myState = movementName;
		notifyObservers(myState);
	}
	
	private IAction moveForward = (params) -> {
	        myPhysicsObject.move(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
		setStateName("forward");
	};

	private IAction jump = (params) -> {
		Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
		myPhysicsObject.move(myVector);
		setStateName("jump");
	};

	public IAction getAction(String name) {
		return myActionMap.get(name);
	}
}