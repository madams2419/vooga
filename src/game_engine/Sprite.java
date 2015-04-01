package game_engine;
/**
 * Abstract class for the creation of multiple sprite types
 * @author 
 *
 */
public abstract class Sprite {
	
	String name;
	int id;
	HitBox hitBox;
	PhysicsEngine physics;
	
	/**
	 * Blank Constructor
	 */
	public Sprite() {
		// TODO
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
	public Sprite(String name, int id){
	    this.name = name;
	    this.id = id;
	}
	/**
	 * method update
	 * Updates the sprite
	 */
	public abstract void update();
	
	/**
	 * method setID
	 * sets the ID of the sprite
	 * @param id the int id to set to the sprite
	 */
	public void setID(int id){
	    this.id = id;
	}
	
	/**
	 * method getID
	 * gets the ID of the sprite
	 * @return int Id of the sprite
	 */
	public int getID(){
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

}
