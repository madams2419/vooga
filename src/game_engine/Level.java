package game_engine;

import game_engine.objective.Objective;

import java.util.ArrayList;
import java.util.List;


/**
 * Level contains the information for each different game level and updates 
 * @author Kevin Chang
 *
 */
public class Level {
	
	List<Objective> myObjectives;
	List<Layer> myLayers;
	
	public Level() {
		// TODO
	    myObjectives = new ArrayList<>();
	    myLayers = new ArrayList<>();
	}
	
	/**
         * method update
         * Update contents of a layer
         */
        public void update(long now) {
                // TODO
            //Potentially different method
            myObjectives.forEach(objective -> objective.update(now));
            myLayers.forEach(layer -> layer.update());
        }
        
        public void scrollX(int x){
            myLayers.forEach(layer -> layer.scrollX(x));
        }
        
        public void scrollY(int y){
            myLayers.forEach(layer -> layer.scrollY(y));
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
	public void addLayer(Layer layer){
	    myLayers.add(layer);
	}
	
	/**
	 * method removeLayer
	 * removes a layer from a specific layer
	 * @param layer the layer to be removed
	 */
	public void removeLayer(Layer layer){
	    myLayers.remove(layer);
	}
	
	/**
	 * method getLayers
	 * @return list of layers for the current level
	 */
	public List<Layer> getLayers(){
	    return myLayers;
	}
}
