package game_engine.sprite;

<<<<<<< HEAD
import game_engine.Animation;
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.hitboxes.IHitbox;
import game_engine.physics_engine.Vector;
import game_engine.physics_engine.physics_object.IPhysicsObject;

=======
import game_engine.behaviors.IAction;
import game_engine.behaviors.IActor;
import game_engine.behaviors.IBehavior;
import game_engine.physics.Material;
import game_engine.physics.PhysicsEngine;
import game_engine.physics.PhysicsObject;
import game_engine.physics.RigidBody.RBodyType;
import game_player.Animation;
>>>>>>> c8ccf479f6c7ba762fdf15de079ad7dd859f5fb5
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.image.ImageView;

public class Sprite extends Observable implements IActor {
    
	private String myState;
	private Animation myAnimation;
<<<<<<< HEAD
	protected IPhysicsObject myPhysicsObject;
	private Map<String, IAction> myActionMap = new HashMap<>();
	private Map<String, IHitbox> hitboxes;
=======
	protected PhysicsObject myPhysicsObject;
	private Map<String, IBehavior> myBehaviorMap = new HashMap<>();
	
	
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
>>>>>>> c8ccf479f6c7ba762fdf15de079ad7dd859f5fb5
	
	public Sprite(String state, Animation animation, IPhysicsObject physicsObject) {
		myState = state;
		myAnimation = animation;
		myPhysicsObject = physicsObject;
		buildActionMap();
		addObserver(animation);
	}
	
<<<<<<< HEAD
	private void buildActionMap(){ 
		myActionMap.put("moveForward", moveForward);
		myActionMap.put(jump.toString(), jump);
		myActionMap.put("setState", setState);
		
=======
	public void runBehavior(String behavior){
	    myBehaviorMap.get(behavior).perform();
>>>>>>> c8ccf479f6c7ba762fdf15de079ad7dd859f5fb5
	}
	
	public IHitbox getHitbox() {
		return hitboxes.get(myState);
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

<<<<<<< HEAD
        private IAction jump = (params) -> {
                Vector myVector = new Vector(0,1*Double.parseDouble(params[0]));
                myPhysicsObject.move(myVector);
        };

        public IAction getAction(String name) {
                return myActionMap.get(name);
        }
}
=======
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
>>>>>>> c8ccf479f6c7ba762fdf15de079ad7dd859f5fb5
