package game_engine;

import game_engine.objective.Objective;
import game_engine.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;


/**
 * Level contains the information for each different game level and updates 
 * @author Kevin Chang
 *
 */
public class Level {
	
	List<Objective> myObjectives;
	List<Sprite> mySprites;
	
	public Level() {
		// TODO
	    myObjectives = new ArrayList<>();
	    mySprites = new ArrayList<>();
	}
	
	/**
         * method update
         * Update contents of a layer
         */
        public void update(long now) {
                // TODO
            //Potentially different method
            myObjectives.forEach(objective -> objective.update(now));
            mySprites.forEach(sprite -> sprite.update());
        }
        
	/**
	 * method addObjective
	 * adds a Game objective to the current level
	 * @param objective the GameObjective to be added
	 */
	public void addObjective(Objective objective){
	    myObjectives.add(objective);
	}
	
	/**
	 * method removeObjective
	 * removes a Game objective from the current level
	 * @param objective the objective to be removed
	 */
	public void removeObjective(Objective objective){
	    myObjectives.remove(objective);
	}
	
	public void setObjectives(List<Objective> objectives){
	    myObjectives = objectives;
	}
	
	/**
	 * method getObjectives
	 * @return list of objectives for the current level
	 */
	public List<Objective> getObjectives(){
	            return myObjectives;
	}
	
	/**
	 * method addLayer
	 * adds a layer into a specific level
	 * @param layer the layer to be added
	 */
	public void addSprite(Sprite layer){
	    mySprites.add(layer);
	}
	
	/**
	 * method removeLayer
	 * removes a layer from a specific layer
	 * @param layer the layer to be removed
	 */
	public void removeSprite(Sprite layer){
	    mySprites.remove(layer);
	}
	
	/**
	 * method getLayers
	 * @return list of layers for the current level
	 */
	public List<Sprite> getSprites(){
	    return mySprites;
	}
}
