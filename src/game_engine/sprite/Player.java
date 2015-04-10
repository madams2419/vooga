package game_engine.sprite;

import game_engine.IBehavior;
import game_engine.physics.PhysicsObject;
import game_engine.physics.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


public class Player extends Character {
        private Map<Collectible, Integer> myCollectibleMap;
        
        public Player(PhysicsObject physics){
            super(physics);
            myCollectibleMap = new HashMap<>();
        }
        
	public Player(PhysicsObject physics, String name) {
		super(physics,name);
		myCollectibleMap = new HashMap<>();
	}
	
	public Player(PhysicsObject physics,String name, int id){
		super(physics,name,id);
		myCollectibleMap = new HashMap<>();
	}
	
	public void addCollectible(Collectible collectible){
	    myCollectibleMap.put(collectible, 0);
	}
	
	public void removeCollectible(Collectible collectible){
	    myCollectibleMap.remove(collectible);
	}
	
	public void incrementCount(Collectible collectible, int amount){
	   myCollectibleMap.put(collectible, myCollectibleMap.get(collectible)+amount); 
	}
	
	public void decrementCount(Collectible collectible, int amount){
	   myCollectibleMap.put(collectible, myCollectibleMap.get(collectible)-amount); 

	}
	
	public void setCount(Collectible collectible, int count){
	    myCollectibleMap.put(collectible, count);
	}
	
	public int getCount(Collectible collectible){
	    return myCollectibleMap.get(collectible);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	    this.getPhysicsObject().update();
	    setChanged();
            notifyObservers();
	}	
	
}
