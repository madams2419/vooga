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
	
	List<Objective> objectives;
	List<Layer> layers;
	
	public Level() {
		// TODO
	    objectives = new ArrayList<>();
	    layers = new ArrayList<>();
	}
	
	/**
         * method update
         * Update contents of a layer
         */
        public void update() {
                // TODO
            //Potentially different method
            objectives.forEach(objective -> objective.update());
            layers.forEach(layer -> layer.update());
        }
        
	/**
	 * method addObjective
	 * adds a Game objective to the current level
	 * @param objective the GameObjective to be added
	 */
	public void addObjective(Objective objective){
	    objectives.add(objective);
	}
	
	/**
	 * method removeObjective
	 * removes a Game objective from the current level
	 * @param objective the objective to be removed
	 */
	public void removeObjective(Objective objective){
	    objectives.remove(objective);
	}
	
	/**
	 * method getObjectives
	 * @return list of objectives for the current level
	 */
	public List<Objective> getObjectives(){
	            return objectives;
	}
	
	/**
	 * method addLayer
	 * adds a layer into a specific level
	 * @param layer the layer to be added
	 */
	public void addLayer(Layer layer){
	    layers.add(layer);
	}
	
	/**
	 * method removeLayer
	 * removes a layer from a specific layer
	 * @param layer the layer to be removed
	 */
	public void removeLayer(Layer layer){
	    layers.remove(layer);
	}
	
	/**
	 * method getLayers
	 * @return list of layers for the current level
	 */
	public List<Layer> getLayers(){
	    return layers;
	}
}
