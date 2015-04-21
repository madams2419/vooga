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
		myActionMap.put(jump.toString(), jump);
		myActionMap.put("setState", setState);
		
	}
	
	public void update() {
	    myPhysicsObject.update();
	}
	
	public ImageView getImageView(){
	    return myAnimation.getImageView();
	}
	
	public IPhysicsObject getPhysicsObject() {
	    return myPhysicsObject;
	}
	
	public void setImageSize(double xSize, double ySize){
	    myAnimation.getImageView().setFitHeight(ySize);
	    myAnimation.getImageView().setFitWidth(xSize);
	}
	
	private IAction setState = (params) -> {
            String state = params[0];
            myState = state;
            setChanged();
            notifyObservers();
        };
        
        public String getState() {
            return myState;
        }
        
        private IAction moveForward = (params) -> {
                myPhysicsObject.move(new Vector(Double.parseDouble(params[0]), Double.parseDouble(params[1])));
        };

        private IAction jump = (params) -> {
                Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
                myPhysicsObject.move(myVector);
        };

        public IAction getAction(String name) {
                return myActionMap.get(name);
        }
}